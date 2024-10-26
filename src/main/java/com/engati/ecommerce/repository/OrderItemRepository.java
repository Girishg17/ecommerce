package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem ,Long> {
    List<OrderItem> findByProductId(Long productId);
}
