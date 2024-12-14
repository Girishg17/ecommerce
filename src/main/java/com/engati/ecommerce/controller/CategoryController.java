package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.CategoryDTO;
import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.responses.CategoryRes;
import com.engati.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryRes> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/fetch/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id) .orElseThrow(() -> new RuntimeException("Merchant not found"));
        return new CategoryDTO(category.getId(), category.getName());
    }
}
