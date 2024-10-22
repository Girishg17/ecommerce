package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.responses.CategoryRes;
import com.engati.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryRes> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
