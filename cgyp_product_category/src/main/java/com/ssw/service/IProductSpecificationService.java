package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSpecification;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-01-05 15:42
 * @Description: 规格实现层接口
 */
public interface IProductSpecificationService {
   //搜索规格
    public ServerResponse search(String specName, Long specId, Integer pageNum, Integer pageSize);

    //添加规格
    public ServerResponse addProductSpec(ProductSpecification productSpecification);

    //修改规格
    public ServerResponse updateProductSpec(ProductSpecification productSpecification);

    //删除规格
    public ServerResponse deleteProductSpecById(Long id);
}
