package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductBrand;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-01-07 16:35
 * @Description: 商品品牌service层接口
 */
public interface IProductBrandService {

    //后台商品品牌搜索
    ServerResponse search(String name, Long id, Integer pageNum, Integer pageSize);
    //增加商品品牌
    ServerResponse addBrand(ProductBrand productBrand);
    //修改商品品牌
    ServerResponse updateBrand(ProductBrand productBrand);
    //删除商品品牌
    ServerResponse deleteBrand(Long id);
    //品牌列表
    ServerResponse brandList();
}
