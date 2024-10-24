package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
