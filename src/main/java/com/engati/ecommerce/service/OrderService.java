package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.CartItDto;
import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.request.OrderState;
import java.util.List;


public interface OrderService {
    public void createOrderForUser(Long userId, List<CartItDto> cartItems, Long cartId);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersOfMerchant(Long merchantId);
    Order updateOrderStatus(Long orderId,  OrderState status);
    void updateCartItemRatings(Long productId, double newRating);
}
