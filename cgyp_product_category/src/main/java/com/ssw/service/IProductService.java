package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.Product;
import com.ssw.pojo.ProductSubtable;
import com.ssw.vo.ProductDetailVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IProductService {
    public ServerResponse addOrUpdate(Product product);
    //后台商品搜索
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize);
    //商品详情
    public ServerResponse<ProductDetailVO> detail(Integer productId, HttpServletRequest request, HttpServletResponse response);

    //根据商品id查询商品信息
    public ServerResponse<Product> findProductById(Integer productId);
    //扣库存
    public ServerResponse reduceProductStock(Integer productId, Integer stock);
    //查找热销产品
    public ServerResponse isHotProduct(Integer category_id, Integer is_hot);
    //查看新品
    public ServerResponse isNewProduct(Integer pageNum, Integer pageSize);
    //根据商品id改变商品在售状态
    public ServerResponse updateStatus(Integer productId, Integer status);
    //根据商品id改变商品是否新品
    public ServerResponse updateIsnew(Integer productId, Integer isnew);
    //根据商品id改变商品是否热门
    public ServerResponse updateIshot(Integer productId, Integer ishot);
    //根据商品id删除商品
    public ServerResponse deleteById(Integer productId);

    //根据商家id查看商品
    public ServerResponse selectBySellerId(String SellerId, Integer pageNum, Integer pageSize);

    //根据商品规格产看商品
    public ServerResponse seeProDetailBySpec(Integer spuId, String productSpec);

    //查询可兑换的商品
    public ServerResponse convertibleProduct(String convertibleDesc, Integer pageNum, Integer pageSize);

    //获取商品浏览记录
    ServerResponse history(String[] pids ,Integer pageNum, Integer pageSize);
}
