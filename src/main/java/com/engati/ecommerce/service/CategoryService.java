package com.engati.ecommerce.service;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.responses.CategoryRes;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryRes>getAllCategories();
    Optional<Category> findById(Long id);
}
