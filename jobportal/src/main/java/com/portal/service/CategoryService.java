package com.portal.service;

import com.portal.response.CategoryResponse;

import java.util.List;

public interface CategoryService
{
    boolean addCategory(String categoryName);
    boolean deleteCategory(long id);
    List<CategoryResponse> getAllCategory();
}
