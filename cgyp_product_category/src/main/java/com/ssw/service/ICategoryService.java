package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.Category;

public interface ICategoryService {
    //添加类别
    public ServerResponse addCategory(Category category);
    //修改类别  categoryId,categoryName,categoryUrl
    public ServerResponse updateCategory(Category category);
    //删除类别  categoryId
    public ServerResponse deleteCategory(Integer categoryId);
    //查看类别  categoryId,categoryName,categoryUrl
    public ServerResponse getCategoryById(Integer categoryId);
    //查看类别  categoryId,categoryName,categoryUrl
    public ServerResponse deepCategory(Integer categoryId);
    //根据parentId查询类别
    public ServerResponse<Category> selectCategory(Integer categoryId);
    //根据id查看类别信息
    public ServerResponse seeCategoryById(Integer categoryId);
}
