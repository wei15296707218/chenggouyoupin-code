package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductTypeTemplate;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-01-06 15:15
 * @Description: 商品模板service层接口
 */
public interface IProductTypeTemplateService {

    //查看商品模板
    public ServerResponse search(String name, Long id, Integer pageNum, Integer pageSize);

    //增加商品模板
    public ServerResponse addProductTypeTemp(ProductTypeTemplate productTypeTemplate);

    //修改商品模板
    public ServerResponse updateProductTypeTemp(ProductTypeTemplate productTypeTemplate);

    //删除商品模板
    public ServerResponse deleteProductTypeTemp(Long id);
}
