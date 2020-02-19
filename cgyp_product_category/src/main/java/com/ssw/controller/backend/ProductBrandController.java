package com.ssw.controller.backend;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductBrand;
import com.ssw.service.IProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-01-07 16:32
 * @Description: 商品品牌
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/manage/productBrand/")
public class ProductBrandController {

    @Autowired
    IProductBrandService productBrandService;

    /*
    * 分页查询品牌*/

    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(name="brandName",required = false) String name,
                                 @RequestParam(name="brandId",required = false) Long id,
                                 @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize){

        return productBrandService.search(name, id, pageNum, pageSize);

    }
    /*
    * 品牌列表*/
    @RequestMapping("brandList")
    public ServerResponse brandList(){
        return productBrandService.brandList();
    }
    /*
    * 增加品牌*/
    @RequestMapping("add.do")
    public ServerResponse addBrand(ProductBrand productBrand){

        return productBrandService.addBrand(productBrand);
    }

    /*
    * 修改品牌信息
    * */
    @RequestMapping(value = "set.do")
    public ServerResponse updateBrand(ProductBrand productBrand){

        return productBrandService.updateBrand(productBrand);
    }

    /*
    * 删除商品品牌*/
    @RequestMapping(value = "delete.do")
    public ServerResponse deleteBrand(Long id){

        return productBrandService.deleteBrand(id);
    }
}