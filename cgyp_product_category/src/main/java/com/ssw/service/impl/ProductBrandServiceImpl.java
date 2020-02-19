package com.ssw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductBrandMapper;
import com.ssw.pojo.ProductBrand;
import com.ssw.service.IProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-01-07 16:35
 * @Description: 商品品牌service实现
 */
@Service
public class ProductBrandServiceImpl implements IProductBrandService {

    @Autowired
    ProductBrandMapper brandMapper;

    /*
    * 商品品牌查询*/
    @Override
    public ServerResponse search(String name, Long id, Integer pageNum, Integer pageSize) {
        if (name!=null){
            name="%"+name+"%";
        }
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<ProductBrand> productBrandList=brandMapper.findBrandsByNameAndId(id,name);
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    /*
     * 商品品牌增加*/
    @Override
    public ServerResponse addBrand(ProductBrand productBrand) {

        if (productBrand==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        List<ProductBrand>  productBrandList = brandMapper.selectAll();
        for (ProductBrand productBrand1 : productBrandList){
            if (productBrand.getName().equalsIgnoreCase(productBrand1.getName())){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"不可添加重复品牌");
            }
        }
        int result = brandMapper.insert(productBrand);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"增加失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse updateBrand(ProductBrand productBrand) {
        if (productBrand==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        List<ProductBrand>  productBrandList = brandMapper.selectAll();
        for (ProductBrand productBrand1 : productBrandList){
            if (productBrand.getName().equalsIgnoreCase(productBrand1.getName())){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"不可添加重复品牌");
            }
        }
       int result =  brandMapper.updateByPrimaryKey(productBrand);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"修改失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse deleteBrand(Long id) {
        if (id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"品牌id必须传值");
        }
       int result =  brandMapper.deleteByPrimaryKey(id);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse brandList() {
        List<ProductBrand> productBrandList = brandMapper.selectAll();
        return ServerResponse.createServerResponseBySuccess(productBrandList);
    }
}