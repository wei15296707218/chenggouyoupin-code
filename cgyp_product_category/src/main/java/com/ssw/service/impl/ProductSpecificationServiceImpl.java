package com.ssw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductSpecificationMapper;
import com.ssw.dao.ProductSpecificationOptionMapper;
import com.ssw.pojo.ProductSpecification;
import com.ssw.service.IProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-01-05 15:46
 * @Description: 规格实现层
 */
@Service
public class ProductSpecificationServiceImpl implements IProductSpecificationService {

    @Autowired
    ProductSpecificationMapper productSpecificationMapper;

    @Autowired
    ProductSpecificationOptionMapper optionMapper;

    @Override
    public ServerResponse search(String specName, Long specId, Integer pageNum, Integer pageSize) {
        if (specName!=null){
            specName="%"+specName+"%";
        }
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<ProductSpecification> productSpecificationList =  productSpecificationMapper.findProDecsByNameAndId(specId,specName);
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse addProductSpec(ProductSpecification productSpecification) {
        if (productSpecification==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        List<ProductSpecification> productSpecificationList = productSpecificationMapper.selectAll();
        for(ProductSpecification productSpecifications :productSpecificationList ){
            if (productSpecification.getSpecName().equalsIgnoreCase(productSpecifications.getSpecName())){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"不可添加相同的名字");
            }
        }
        int result =  productSpecificationMapper.insert(productSpecification);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"规格添加失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse updateProductSpec(ProductSpecification productSpecification) {
        if (productSpecification==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        if (productSpecification.getSpecName()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"规格名字必传");
        }
        int result = productSpecificationMapper.updateByPrimaryKey(productSpecification);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse deleteProductSpecById(Long id) {
        if (id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
       int result1 =  productSpecificationMapper.deleteByPrimaryKey(id);
       int result2 = optionMapper.deleteBatch(id);
        if (result1<=0 & result2<=0){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }
}