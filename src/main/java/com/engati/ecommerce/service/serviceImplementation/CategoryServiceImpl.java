package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.repository.CategoryRepository;
import com.engati.ecommerce.responses.CategoryRes;
import com.engati.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryRes> getAllCategories() {
        return categoryRepository.getCategories();
    }
}
