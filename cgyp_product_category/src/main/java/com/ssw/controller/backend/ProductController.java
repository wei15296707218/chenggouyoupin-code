package com.ssw.controller.backend;

import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.Product;
import com.ssw.pojo.ProductSubtable;
import com.ssw.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@RequestMapping(value = "/manage/product/")
public class ProductController {

    @Autowired
    IProductService productService;

    /*
     *商品添加&更新*/
    @RequestMapping(value = "save.do")
    public ServerResponse addOrUpdate(Product product){

        return productService.addOrUpdate(product);
    }
    /*
     *搜索商品*/
    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(name="productName",required = false) String productName,
                                 @RequestParam(name="productId",required = false) Integer productId,
                                 @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize){

        return productService.search(productName, productId, pageNum, pageSize);

    }
    /*
     *商品详情*/
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(Integer productId , HttpServletRequest request, HttpServletResponse response){

        return productService.detail(productId, request,response);
    }
    /*
     *查看是否是热销产品*/
    @RequestMapping("hot.do")
    public ServerResponse isHotProduct(Integer category_id,Integer is_hot){

            return productService.isHotProduct(category_id,is_hot);

    }
    /*
     *查看是否是新品*/
    @RequestMapping("new.do")
    public ServerResponse isNewProduct(@RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                       @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize){

        return productService.isNewProduct(pageNum,pageSize);

    }
   /*
    *根据商品id改变商品在售状态*/
    @RequestMapping("updatastatus.do")
    public ServerResponse updateStatus(Integer productId,Integer status){

        return productService.updateStatus(productId,status);

        }
    /*
     *根据商品id改变是否新品*/
    @RequestMapping("updateisnew.do")
    public ServerResponse updateIsnew(Integer productId, Integer isnew){

        return productService.updateIsnew(productId ,isnew);

    }
    /*
     *根据商品id改变是否热门*/
    @RequestMapping("updateishot.do")
    public ServerResponse updateIshot(Integer productId ,Integer ishot){

        return productService.updateIshot(productId , ishot);

    }
    /*
     *根据商品id删除商品*/
    @RequestMapping("delete.do")
    public ServerResponse deleteById(Integer productId){

        return productService.deleteById(productId);

    }

    /*
     *根据商家id查看商品*/
    @RequestMapping("seller_pro.do")
    public ServerResponse selectBySellerId(String SellerId,
                                           @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                           @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize){

        return productService.selectBySellerId(SellerId,pageNum,pageSize);
    }

    /*
    * 根据规格查看商品详情*/
    @RequestMapping("spec_pro.do")
    public ServerResponse seeProDetailBySpec(Integer spuId ,String productSpec){

        return productService.seeProDetailBySpec(spuId,productSpec);

    }

    /*
     *查询可兑换的商品*/
    @RequestMapping("convertible_pro.do")
    public ServerResponse convertibleProduct(String convertibleDesc,
                                             @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                             @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize){

        return productService.convertibleProduct(convertibleDesc,pageNum,pageSize);
    }

    /**
     * 获取历史
     * @param req
     * @return
     */
    @RequestMapping(value = "history")
    public ServerResponse history(HttpServletRequest req,
                                  @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                  @RequestParam(required = false,defaultValue = "10")Integer pageSize) {
        Cookie[] cookies = req.getCookies();
        String[] pids = null;
        for (int i = 0; cookies != null && i < cookies.length; i++)
        {
            //System.out.println(cookies[i].getName());
            //找到我们想要的cookie
            if (cookies[i].getName().equals("producthistoryid")) {
               // System.out.println(cookies[i].getValue());
                pids = cookies[i].getValue().split("#");

                //得到cookie中存在的id，展现浏览过的商品
            }


        }
        return productService.history(pids, pageNum, pageSize);

    }
}
