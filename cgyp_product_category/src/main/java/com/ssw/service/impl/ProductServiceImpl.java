package com.ssw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductMapper;
import com.ssw.dao.ProductSubtableMapper;
import com.ssw.pojo.Category;
import com.ssw.pojo.Product;
import com.ssw.pojo.ProductSubtable;
import com.ssw.service.ICategoryService;
import com.ssw.service.IProductService;
import com.ssw.utils.DateUtils;
import com.ssw.utils.RedisApi;
import com.ssw.vo.ProductDetailVO;
import com.ssw.vo.ProductListVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ICategoryService categoryService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductSubtableMapper subtableMapper;
    @Autowired
    RedisApi redisApi;
    @Value("${business.productInsertPrimaryKey}")
    private String key;
    @Value("${business.imageHost}")
    private String imageHost;

    @Override
    public ServerResponse addOrUpdate(Product product) {
        if (product==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        //subimages 1.jpg 2.jpg 3.png
        //2.设置商品的主图 sub_images-->1.jpg 2.jpg 3.png
        String subImages=product.getSubImages();
        if (subImages!=null&&!subImages.equals("")){
            String[] subImageArr=subImages.split(",");
            if (subImageArr.length>0){
                //设置商品的主图
                product.setMainImage(subImageArr[0]);
            }
        }
        Integer productId=product.getId();
        if (productId==null)
        {
            //添加
            int result=productMapper.insert(product);
            String id = product.getId().toString();
            redisApi.set(key,id);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"添加失败");
            }else{
                return ServerResponse.createServerResponseBySuccess();
            }
        }else{
            //更新
            int result=productMapper.updateByPrimaryKey(product);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
            }else{
                return ServerResponse.createServerResponseBySuccess();
            }
        }
    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        if (productName!=null){
            productName="%"+productName+"%";
        }
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Product> productList=productMapper.findProductsByNameAndId(productId,productName);
       // ProductSubtable productSubtable = subtableMapper.selectByProId(productId);
        List<ProductListVO> prodectListVOList = Lists.newArrayList();
        //List<Product>  -->  List<ProductListVO>
        /*if (productSubtable==null){
            return ServerResponse.createServerResponseBySuccess();
        }*/
        if(productList!=null&&productList.size()>0){
            for (Product product:productList){
                //product->productListVO
                ProductListVO productListVO=assembleProductListVO(product);
                prodectListVOList.add(productListVO);
        }
        }

        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO=new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());

        /*productListVO.setBrandId(productSubtable.getBrandId());
        productListVO.setTypeTemplateId(productSubtable.getTypeTemplateId());
        productListVO.setSpu(productSubtable.getSpu());
        productListVO.setSellerId(productSubtable.getSellerId());
        productListVO.setDefaultItemId(productSubtable.getDefaultItemId());
        productListVO.setSpecificationItems(productSubtable.getSpecificationItems());
        productListVO.setSaleService(productSubtable.getSaleService());
        productListVO.setPackageList(productSubtable.getPackageList());
        productListVO.setIsEnableSpec(productSubtable.getIsEnableSpec());*/
        return  productListVO;
    }
    public ServerResponse<ProductDetailVO> detail(Integer productId, HttpServletRequest request, HttpServletResponse response){
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        //查询缓存
        String skuKey = "product:"+productId+":detail";
        String detailJson = redisApi.get(skuKey);
        if (StringUtils.isNotBlank(detailJson)){//相当于detailJson!=null&&!detailJson.equals("")
            //不为空,缓存存在该商品详情信息
            //将字符串转换为json数据;
            ProductDetailVO productDetailVO = JSON.parseObject(detailJson,ProductDetailVO.class);
            return ServerResponse.createServerResponseBySuccess(productDetailVO);
        }else{
            //缓存不存在该商品详情信息,将在mysql中查询
            //解决缓存击穿
            //设置分布式锁
            String skuKeyLock = "product:"+productId+":lock";
            String token = UUID.randomUUID().toString();
            String result = redisApi.set(skuKeyLock,token,"nx","px",10*1000);
            if (StringUtils.isNotBlank(result)&&result.equalsIgnoreCase("ok")) {
                //设置成功,有权在10秒的过期时间访问数据库
                Product product = productMapper.selectByPrimaryKey(productId);
                ProductSubtable productSubtable = subtableMapper.selectByProId(productId);
                if (product == null) {
                    return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "未找到商品");
                }

                //将商品的信息存入cookie,
                String id = productId + "";
                String producthistoryid = "";
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        String name = cookie.getName();
                        String value = cookie.getValue();
                        if (name.equals("producthistoryid")) {
                            //存在producthistoryid这个cookie的话，就将它赋值给producthistoryid这个变量
                            producthistoryid = value;
                            //System.out.println(producthistoryid);
                        }
                    }
                }
                if (producthistoryid == null || producthistoryid.equals("")) {
                    id += "#";
                    //System.out.println(id);
                    Cookie producthistoryCookie = new Cookie("producthistoryid", id);
                    response.addCookie(producthistoryCookie);
                } else {
                    //存在producthistoryid这个Cookie时，就将最近浏览的id添加到字符串的最前面
                    //要保证最新浏览的商品id在最前面，有两种实现方式，当最下面哪种更简便
                    //方式一：
//       String historyid="";
//       List<String> maskList=new ArrayList<String>();
//       String[] masks=producthistoryid.split(",");
//       for (String mask: masks){
//          if(!mask.equals(id)){
//             maskList.add(mask);
//          }
//       }
//         //ConvertUtil是将ArrayList里面的内容转换成字符串的一个帮助类
//       String rs=ConvertUtil.convertByMask(maskList, ",");
//       rs=id+","+rs;
                    //或者这样实现，三行代码就可以搞定
                    producthistoryid = "#" + producthistoryid;
                    producthistoryid = producthistoryid.replace("#" + id + "#", "#");
                    String rs = id + producthistoryid;

                    Cookie producthistoryCookie = new Cookie("producthistoryid", rs);
                    //System.out.println(producthistoryCookie.getValue());
                    //System.out.println(producthistoryCookie.getName());
                    response.addCookie(producthistoryCookie);
                }
                //product &productSubtable -->  productDetailVo

                if (productSubtable == null) {
                    return ServerResponse.createServerResponseBySuccess(product);
                }
                ProductDetailVO productDetailVO = assembleProductDetailVO(product,productSubtable);
                redisApi.set(skuKey, JSON.toJSONString(productDetailVO));
                //在访问完mysql之后,释放redis事务锁
                String lockToken = redisApi.get(skuKeyLock);
                if (StringUtils.isNotBlank(lockToken) && lockToken.equalsIgnoreCase(token)){
                    redisApi.del(skuKeyLock);
                }

                return ServerResponse.createServerResponseBySuccess(productDetailVO);


            }else{
                //设置失败,自旋(该线程在睡眠几秒后重新访问详情接口)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return detail(productId,request,response);
            }
        }

    }

    /*查看商品浏览记录*/
    @Override
    public ServerResponse history(String[] pids, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductListVO> productListVOS = Lists.newArrayList();
        List<Product> products=Lists.newArrayList();
        int count=pids.length;//数量
        if (pids.length!=0){
            for (String pid:pids
                    ) {
                //一个一个遍历出来
                Integer productId=Integer.parseInt(pid);
                Product product= productMapper.selectByPrimaryKey(productId);
               // ProductSubtable productSubtable = subtableMapper.selectByProId(productId);
               // ProductDetailVO productDetailVO=assembleProductDetailVO(product,productSubtable);
                products.add(product);
            }
        }

        // List<Product>---> List<ProductListVO>
        if (products != null && products.size() > 0) {
            for (Product product : products
                    ) {
                ProductListVO productListVO = assembleProductListVO(product);
                productListVOS.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(productListVOS);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
    @Override
    public ServerResponse findProductById(Integer productId) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        ProductSubtable productSubtable = subtableMapper.selectByProId(productId);
        ProductDetailVO productDetailVO=assembleProductDetailVO(product,productSubtable);
        return ServerResponse.createServerResponseBySuccess(productDetailVO);
    }

    @Override
    public ServerResponse reduceProductStock(Integer productId, Integer stock) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }

        if (stock==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品库存毕传!");
        }
           int result =  productMapper.reduceProductStock(productId,stock);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"扣库存失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse isHotProduct(Integer category_id, Integer is_hot) {
        if (category_id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"类别id必须传值");
        }
        if (is_hot==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"热销状态必须传值");
        }
        List<Product> products=productMapper.isHotProduct(category_id,is_hot);
        if (products==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        return ServerResponse.createServerResponseBySuccess(products);
    }

    @Override
    public ServerResponse isNewProduct(Integer pageNum, Integer pageSize) {

        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Product> products=productMapper.isNewProduct();
        if (products==null && products.size()<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse updateStatus(Integer productId, Integer status) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
            int result = productMapper.updateStatus(productId,status);
        if (result!=1){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
            return ServerResponse.createServerResponseBySuccess();

    }

    @Override
    public ServerResponse updateIsnew(Integer productId , Integer isnew) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
        int result = productMapper.updateIsnew(productId,isnew);
        if (result!=1){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse updateIshot(Integer productId, Integer ishot) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
        int result = productMapper.updateIshot(productId,ishot);
        if (result!=1){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse deleteById(Integer productId) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
        int result1 = productMapper.deleteById(productId);
        int result2 = subtableMapper.deleteById(productId);
        if (result1!=1 && result2!=1){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    /*
    * 根据商家id查看商品*/
    @Override
    public ServerResponse selectBySellerId(String SellerId , Integer pageNum, Integer pageSize) {
        if (SellerId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id必须传值");
        }
        SellerId="%"+SellerId+"%";
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<ProductSubtable> productSubtableList = subtableMapper.selectBySellerId(SellerId);
        List<ProductDetailVO> productDetailVOList = Lists.newArrayList();
        if (productSubtableList != null && productSubtableList.size() > 0){
            for (ProductSubtable productSubtable : productSubtableList){
                Product product = productMapper.selectByPrimaryKey(productSubtable.getProductId());
                ProductDetailVO productDetailVO=assembleProductDetailVO(product,productSubtable);
                productDetailVOList.add(productDetailVO);
            }
        }
        PageInfo pageInfo=new PageInfo(productDetailVOList);
        //return ServerResponse.createServerResponseBySuccess(productDetailVOList);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    /*
    * 通过商品规格查看商品*/
    @Override
    public ServerResponse seeProDetailBySpec(Integer spuId, String productSpec) {
        if (spuId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品spuid必须传值");
        }
        if (productSpec==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品规格名称必须传值");
        }
        //查询缓存
        String skuKey = "product:"+spuId+"/"+productSpec+":Spec";
        String DetailBySpecJson = redisApi.get(skuKey);
        if (StringUtils.isNotBlank(DetailBySpecJson)){//相当于DetailBySpecJson!=null&&!DetailBySpecJson.equals("")
            //不为空,缓存存在该商品详情信息
            //将字符串转换为json数据;
            ProductDetailVO productDetailVO = JSON.parseObject(DetailBySpecJson,ProductDetailVO.class);
            return ServerResponse.createServerResponseBySuccess(productDetailVO);
        }else {
            //缓存不存在该商品详情信息,将在mysql中查询
            //解决缓存击穿
            //设置分布式锁
            String skuKeyLock = "product:" + spuId + "/" + productSpec + ":lock";
            //防止线程1的锁过期后执行程序完毕,回来删除线程2正在执行程序的锁,生成一个token,作为value值.
            String token = UUID.randomUUID().toString();
            String result = redisApi.set(skuKeyLock, token, "nx", "px", 10 * 1000);
            if (StringUtils.isNotBlank(result) && result.equalsIgnoreCase("ok")) {
                //设置成功,有权在10秒的过期时间访问数据库
                productSpec = "%" + productSpec + "%";
                ProductSubtable productSubtable = subtableMapper.seeProDetailBySpec(spuId,productSpec);
                if (productSubtable==null){
                    return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品不存在");
                }
                Product product =  productMapper.selectByPrimaryKey(productSubtable.getProductId());
                ProductDetailVO productDetailVO = assembleProductDetailVO(product,productSubtable);
                if (productDetailVO != null){
                    redisApi.set(skuKey,JSON.toJSONString(productDetailVO));
                    //在访问完mysql之后,释放redis事务锁
                    //为自己的锁生成一个token如果此时获取的token值相同,就删除
                    String lockToken = redisApi.get(skuKeyLock);
                    if (StringUtils.isNotBlank(lockToken) && lockToken.equalsIgnoreCase(token)){
                        redisApi.del(skuKeyLock);
                    }

                    return ServerResponse.createServerResponseBySuccess(productDetailVO);
                }else {
                    //数据库中不存在该商品,
                    //为了防止缓存穿透,null或者空字符串设给redis
                    redisApi.setex(skuKey,60*3,JSON.toJSONString(productDetailVO));
                    return ServerResponse.createServerResponseBySuccess();
                }
            }else{
                //设置失败,自旋(该线程在睡眠几秒后重新访问详情接口)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return seeProDetailBySpec(spuId,productSpec);
            }
        }
    }

    /*
    * 可兑换的商品*/
    @Override
    public ServerResponse convertibleProduct(String convertibleDesc, Integer pageNum, Integer pageSize) {
        if (convertibleDesc==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品convertibleDesc必须传值");
        }
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<ProductSubtable> productSubtableList =   subtableMapper.selectConvertibleProduct(convertibleDesc);
        List<ProductDetailVO> productDetailVOList = Lists.newArrayList();
        if (productSubtableList != null && productSubtableList.size() > 0){
            for (ProductSubtable productSubtable : productSubtableList){
                Product product = productMapper.selectByPrimaryKey(productSubtable.getProductId());
                ProductDetailVO productDetailVO=assembleProductDetailVO(product,productSubtable);
                productDetailVOList.add(productDetailVO);
            }
        }
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(productDetailVOList);
        //return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    private ProductDetailVO assembleProductDetailVO(Product product, ProductSubtable productSubtable){
        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(imageHost);
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setNew(product.getIsNew());
        productDetailVO.setHot(product.getIsHot());
        productDetailVO.setBanner(product.getIsBanner());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
    //    Category category= categoryMapper.selectByPrimaryKey(product.getCategoryId());
        ServerResponse<Category> serverResponse= categoryService.selectCategory(product.getCategoryId());
        Category category=serverResponse.getData();
        if (category!=null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        productDetailVO.setBrandId(productSubtable.getBrandId());
        productDetailVO.setTypeTemplateId(productSubtable.getTypeTemplateId());
        productDetailVO.setSpu(productSubtable.getSpu());
        productDetailVO.setSellerId(productSubtable.getSellerId());
        productDetailVO.setDefaultItemId(productSubtable.getDefaultItemId());
        productDetailVO.setSpecificationItems(productSubtable.getSpecificationItems());
        productDetailVO.setSaleService(productSubtable.getSaleService());
        productDetailVO.setPackageList(productSubtable.getPackageList());
        productDetailVO.setIsEnableSpec(productSubtable.getIsEnableSpec());
        return productDetailVO;
    }
}
