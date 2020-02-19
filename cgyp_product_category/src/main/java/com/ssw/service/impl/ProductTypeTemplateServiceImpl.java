package com.ssw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductTypeTemplateMapper;
import com.ssw.pojo.ProductTypeTemplate;
import com.ssw.service.IProductTypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-01-06 15:17
 * @Description: 商品模板service实现
 */
@Service
public class ProductTypeTemplateServiceImpl implements IProductTypeTemplateService {

    @Autowired
    ProductTypeTemplateMapper templateMapper;
    /*
    * 商品模板查看*/
    @Override
    public ServerResponse search(String name, Long id, Integer pageNum, Integer pageSize) {
        if (name!=null){
            name="%"+name+"%";
        }
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<ProductTypeTemplate> typeTemplateList = templateMapper.findProTypeTempByNameAndId(id,name);
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    /*
    * 商品模板添加*/
    @Override
    public ServerResponse addProductTypeTemp(ProductTypeTemplate productTypeTemplate) {
        if (productTypeTemplate==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        List<ProductTypeTemplate> templateList =  templateMapper.selectAll();
        for (ProductTypeTemplate productTypeTemplate1 : templateList){
            if (productTypeTemplate.getName().equalsIgnoreCase(productTypeTemplate1.getName())){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"不能添加相同的模板名字");
            }
        }
        int result = templateMapper.insert(productTypeTemplate);
        if (result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"模板添加失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    /*
    * 商品模板修改*/
    @Override
    public ServerResponse updateProductTypeTemp(ProductTypeTemplate productTypeTemplate) {

        if (productTypeTemplate==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }

        int result = templateMapper.updateByPrimaryKey(productTypeTemplate);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse deleteProductTypeTemp(Long id) {

        if (id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        int result = templateMapper.deleteByPrimaryKey(id);
        if (result<=0){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }
}