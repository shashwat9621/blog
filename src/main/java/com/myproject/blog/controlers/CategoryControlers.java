package com.myproject.blog.controlers;

import com.myproject.blog.dto.CategoryDto;
import com.myproject.blog.payloads.ApiResponse;

import com.myproject.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryControlers {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto creteCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(creteCategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer uid)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,uid);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer uid){
        this.categoryService.deleteCategories((uid));
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategorys(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));

    }
}
