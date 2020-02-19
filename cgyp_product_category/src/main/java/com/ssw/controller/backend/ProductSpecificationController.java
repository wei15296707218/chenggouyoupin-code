package com.ssw.controller.backend;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSpecification;
import com.ssw.service.IProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-01-05 15:33
 * @Description: 商品规格
 */
@CrossOrigin
@RestController
@RequestMapping(value="/manage/proSpecification/")
public class ProductSpecificationController {

    @Autowired
    IProductSpecificationService productSpecificationService;
  /*
  * 查询规格
  */
  @RequestMapping(value = "search.do")
  public ServerResponse search(@RequestParam(name="specName",required = false) String specName,
                               @RequestParam(name="specId",required = false) Long specId,
                               @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(name="pageSize",required = false,defaultValue = "5") Integer pageSize){

      return productSpecificationService.search(specName, specId, pageNum, pageSize);

  }
  /*
  * 增加规格*/
  @RequestMapping("add.do")
  public ServerResponse addProductSpec(ProductSpecification productSpecification){

      return productSpecificationService.addProductSpec(productSpecification);
  }

  /*修改规格*/
  @RequestMapping(value = "set.do")
  public ServerResponse updateProductSpec(ProductSpecification productSpecification){

      return productSpecificationService.updateProductSpec(productSpecification);
  }

  /*删除规格*/
  @RequestMapping(value = "delete.do")
  public ServerResponse deleteProductSpecById(Long id){

      return productSpecificationService.deleteProductSpecById(id);
  }
}