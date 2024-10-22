package com.engati.ecommerce.service;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.responses.CategoryRes;

import java.util.List;

public interface CategoryService {
    List<CategoryRes>getAllCategories();
}
