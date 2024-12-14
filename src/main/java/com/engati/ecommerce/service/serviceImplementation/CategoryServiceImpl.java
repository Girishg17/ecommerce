package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.repository.CategoryRepository;
import com.engati.ecommerce.responses.CategoryRes;
import com.engati.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryRes> getAllCategories() {
        return categoryRepository.getCategories();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

}
