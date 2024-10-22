package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.responses.CategoryRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT new com.engati.ecommerce.responses.CategoryRes(c.id, c.name) FROM Category c")
    List<CategoryRes> getCategories();

}
