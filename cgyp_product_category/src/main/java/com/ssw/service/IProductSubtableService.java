package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.ProductSubtable;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-01-07 11:16
 * @Description: 商品副表service接口
 */
public interface IProductSubtableService {

    //商品副表-新增
    public ServerResponse add(ProductSubtable productSubtable);

    //商品副表-删除(根据商品id)
    public ServerResponse deleteById(Integer productId);

    //商品副表-更新(根据商品id)
    public ServerResponse updateByProId(ProductSubtable productSubtable);
}
