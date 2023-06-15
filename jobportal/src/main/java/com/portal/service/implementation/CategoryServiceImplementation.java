package com.portal.service.implementation;

import com.portal.entity.Category;
import com.portal.repository.CategoryRepository;
import com.portal.response.CandidateResponse;
import com.portal.response.CategoryResponse;
import com.portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public boolean addCategory(String categoryName) {
        Category category=new Category();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean deleteCategory(long id)
    {
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList=categoryRepository.findAll();
        List<CategoryResponse>categoryResponseList=categoryList.stream().map(category->{
            CategoryResponse categoryResponse=new CategoryResponse();
            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setCategoryName(category.getCategoryName());
            return categoryResponse;
        }).collect(Collectors.toList());
        return categoryResponseList;
    }
}
