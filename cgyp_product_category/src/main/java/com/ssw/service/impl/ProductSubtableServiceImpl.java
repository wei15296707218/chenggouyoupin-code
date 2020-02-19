package com.ssw.service.impl;

import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductSubtableMapper;
import com.ssw.pojo.ProductSubtable;
import com.ssw.service.IProductSubtableService;
import com.ssw.utils.RedisApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-01-07 11:20
 * @Description: 商品附表service实现层
 */
@Service
public class ProductSubtableServiceImpl implements IProductSubtableService {

    @Autowired
    ProductSubtableMapper productSubtableMapper;

    @Autowired
    RedisApi redisApi;
    @Value("${business.productInsertPrimaryKey}")
    private String key;
   /*
   * 商品副表-新增商品*/
    @Override
    public ServerResponse add(ProductSubtable productSubtable) {
        String value = redisApi.get(key);
        if (StringUtils.isNotBlank(value)){
            Integer productId = Integer.parseInt(value);
            productSubtable.setProductId(productId);
            ProductSubtable productSubtable1 = productSubtableMapper.selectByProId(productId);
            if (productSubtable1==null){
                productSubtable.setSpu(productId);
            }else {

            }
        }else {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品添加失败1");
        }

        if (productSubtable==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        if (productSubtable.getBrandId()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"品牌号必传");
        }
        if (productSubtable.getSellerId()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"卖家号必传");
        }
        if (productSubtable.getProductId()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品号必传");
        }
        if (productSubtable.getSpu()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"spu号必传");
        }
        int result = productSubtableMapper.insert(productSubtable);
           if (result <= 0){
               return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"添加失败2");
           }
        return ServerResponse.createServerResponseBySuccess();
    }

    /*
    * 商品副表-删除商品*/
    @Override
    public ServerResponse deleteById(Integer productId) {
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        int result = productSubtableMapper.deleteById(productId);
        if (result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    /*
     * 商品副表-更新商品*/
    @Override
    public ServerResponse updateByProId(ProductSubtable productSubtable) {
        if (productSubtable==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        if (productSubtable.getBrandId()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"品牌号必传");
        }
        int result = productSubtableMapper.updateByProId(productSubtable);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }



}