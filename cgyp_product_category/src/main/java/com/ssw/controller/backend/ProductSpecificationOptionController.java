package com.ssw.controller.backend;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSpecificationOption;
import com.ssw.service.IProductSpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-01-05 19:17
 * @Description: 商品规格选项
 */
@CrossOrigin
@RestController
@RequestMapping(value="/manage/proSpecificationOption/")
public class ProductSpecificationOptionController {

    @Autowired
    IProductSpecificationOptionService productSpecificationOptionService;

    /*
    * 根据规格id查询规格选项
    * */
    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(name="specId",required = false) Long specId,
                                 @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "5") Integer pageSize){

        return productSpecificationOptionService.findBySpecId(specId, pageNum, pageSize);

    }
    /*
     * 增加规格选项*/
    @RequestMapping("add.do")
    public ServerResponse addProductSpec(ProductSpecificationOption productSpecificationOption){

        return productSpecificationOptionService.addProductSpecOpt(productSpecificationOption);
    }

    /*修改规格选项*/
    @RequestMapping(value = "set.do")
    public ServerResponse updateProductSpec(ProductSpecificationOption productSpecificationOption){

        return productSpecificationOptionService.updateProductSpecOpt(productSpecificationOption);
    }

    /*删除规格*/
    @RequestMapping(value = "delete.do")
    public ServerResponse deleteProductSpecOptById(Long id){

        return productSpecificationOptionService.deleteProductSpecOptById(id);
    }
}