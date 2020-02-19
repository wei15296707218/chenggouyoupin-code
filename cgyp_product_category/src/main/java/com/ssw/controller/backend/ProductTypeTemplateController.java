package com.ssw.controller.backend;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductTypeTemplate;
import com.ssw.service.IProductTypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-01-06 15:11
 * @Description: 商品模板controller层
 */
@CrossOrigin
@RestController
@RequestMapping(value="/manage/proTypeTemplation/")
public class ProductTypeTemplateController {

    @Autowired
    IProductTypeTemplateService productTypeTemplateService;

    /*
    * 查看模板*/
    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(name="name",required = false) String name,
                                 @RequestParam(name="id",required = false) Long id,
                                 @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "5") Integer pageSize){

        return productTypeTemplateService.search(name, id, pageNum, pageSize);

    }

    /*
     * 增加模板*/
    @RequestMapping("add.do")
    public ServerResponse addProductTypeTemp(ProductTypeTemplate productTypeTemplate){

        return productTypeTemplateService.addProductTypeTemp(productTypeTemplate);
    }


    /*修改模板*/
    @RequestMapping(value = "set.do")
    public ServerResponse updateProductTypeTemp(ProductTypeTemplate productTypeTemplate){

        return productTypeTemplateService.updateProductTypeTemp(productTypeTemplate);
    }

    /*删除模板*/
    @RequestMapping(value = "delete.do")
    public ServerResponse deleteProductTypeTemp(Long id){

        return productTypeTemplateService.deleteProductTypeTemp(id);
    }
}