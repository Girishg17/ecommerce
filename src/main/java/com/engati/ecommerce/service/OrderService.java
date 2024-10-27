package com.engati.ecommerce.service;

import com.engati.ecommerce.model.entity.CartItem;
import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.model.entity.User;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.request.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public void createOrderForUser(Long userId, List<CartItem> cartItems,Long cartId);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersOfMerchant(Long merchantId);
    Order updateOrderStatus(Long orderId,  OrderState status);
}
