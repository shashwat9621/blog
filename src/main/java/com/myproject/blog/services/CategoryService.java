package com.myproject.blog.services;



import com.myproject.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategory();
    void deleteCategories(Integer categoryId);
}
