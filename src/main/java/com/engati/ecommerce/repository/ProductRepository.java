package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByMerchantId(Long merchantId);
    List<Product> findAll();
    List<Product> findByCategory(Category cat);
}
