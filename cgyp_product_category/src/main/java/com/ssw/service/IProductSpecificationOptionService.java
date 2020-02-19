package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSpecificationOption;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-01-05 20:50
 * @Description: 商品规格选项接口层
 */
public interface IProductSpecificationOptionService  {

    //查询规格选项
    public ServerResponse findBySpecId(Long specid, Integer pageNum, Integer pageSize);

    //增加规格选项
    public ServerResponse addProductSpecOpt(ProductSpecificationOption productSpecificationOption);

    //修改规格选项
    public ServerResponse updateProductSpecOpt(ProductSpecificationOption productSpecificationOption);

    //删除规格选项
    public ServerResponse deleteProductSpecOptById(Long id);

}
