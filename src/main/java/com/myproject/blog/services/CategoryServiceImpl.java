package com.myproject.blog.services;

import com.myproject.blog.dto.CategoryDto;
import com.myproject.blog.entities.Category;
import com.myproject.blog.exceptions.ResouceNotFoundException;
import com.myproject.blog.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory=this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow((()-> new ResouceNotFoundException("Category","id",categoryId)));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory= this.categoryRepo.save(category);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category","Id",categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos =categories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public void deleteCategories(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category","Id",categoryId));
        this.categoryRepo.delete(category);
    }
    private Category dtoToCategory(CategoryDto categoryDto)
    {
        Category category = this.modelMapper.map(categoryDto,Category.class);
        return category;
    }
    private  CategoryDto categoryToDto(Category category)
    {
        CategoryDto categoryDto = this.modelMapper.map(category,CategoryDto.class);
        return categoryDto;

    }
}
