package com.ssw.controller.backend;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSubtable;
import com.ssw.service.IProductSubtableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-01-07 10:57
 * @Description: 商品副表
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/manage/productSubTab/")
public class ProductSubtableController {

    @Autowired
    IProductSubtableService productSubtableService;

    /*
    * 商品副表--新增*/
    @RequestMapping(value = "save.do")
    public ServerResponse add(ProductSubtable productSubtable){

        return productSubtableService.add(productSubtable);
    }

    /*
    * 根据商品id删除商品*/
    @RequestMapping("delete.do")
    public ServerResponse deleteById(Integer productId){

        return productSubtableService.deleteById(productId);

    }

    /*
     * 商品副表-更新商品*/
    @RequestMapping(value = "set.do")
    public ServerResponse updateByProId(ProductSubtable productSubtable){

        return productSubtableService.updateByProId(productSubtable);
    }


}