package com.ssw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ProductSpecificationOptionMapper;
import com.ssw.pojo.ProductSpecificationOption;
import com.ssw.service.IProductSpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: business
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-01-05 20:51
 * @Description: 商品规格选项实现层
 */
@Service
public class ProductSpecificationOptionServiceImpl implements IProductSpecificationOptionService {

    @Autowired
    ProductSpecificationOptionMapper optionMapper;

    /*
    * 通过specid 查询规格选项*/
    @Override
    public ServerResponse findBySpecId(Long specid, Integer pageNum, Integer pageSize) {
        if (specid==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        Page page= PageHelper.startPage(pageNum,pageSize);
        optionMapper.selectBySpecid(specid);
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    /*
    * 增加规格选项*/
    @Override
    public ServerResponse addProductSpecOpt(ProductSpecificationOption productSpecificationOption) {
        if (productSpecificationOption==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        List<ProductSpecificationOption> optionList = optionMapper.selectBySpecid(productSpecificationOption.getSpecId());
        for (ProductSpecificationOption productSpecificationOptions : optionList){
            if (productSpecificationOption.getOptionName().equalsIgnoreCase(productSpecificationOptions.getOptionName())){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"不可添加一样的数据");
            }
        }
        int result = optionMapper.insert(productSpecificationOption);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"规格添加失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    /*
     * 修改规格选项*/
    @Override
    public ServerResponse updateProductSpecOpt(ProductSpecificationOption productSpecificationOption) {
        if (productSpecificationOption==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        if (productSpecificationOption.getOptionName()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"规格选项名字必传");
        }
        int result = optionMapper.updateByPrimaryKey(productSpecificationOption);
        if (result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
        }

        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse deleteProductSpecOptById(Long id) {
        if (id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        int result =  optionMapper.deleteByPrimaryKey(id);
        if (result<=0){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }
}