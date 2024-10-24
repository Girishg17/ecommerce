package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.entity.*;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.model.enums.Role;
import com.engati.ecommerce.repository.OrderRepository;
import com.engati.ecommerce.repository.UserRepository;
import com.engati.ecommerce.service.CartService;
import com.engati.ecommerce.service.OrderService;
import com.engati.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;
    @Override
    public void createOrderForUser(Long userId, List<CartItem> cartItems,Long cartId) {
        User user=userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Map<Merchant, List<CartItem>> itemsGroupedByMerchant = cartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getMerchant()));
        for (Map.Entry<Merchant, List<CartItem>> entry : itemsGroupedByMerchant.entrySet()) {
            Merchant merchant = entry.getKey();
            List<CartItem> merchantItems = entry.getValue();

            // Create a new order for the current merchant
            Order order = new Order();
            order.setUser(user);
            order.setMerchant(merchant);

            // Set order items and calculate total amount
            List<OrderItem> orderItems = new ArrayList<>();
            Double totalAmount = 0.0;
            for (CartItem cartItem : merchantItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice());

                totalAmount += cartItem.getQuantity() * cartItem.getProduct().getPrice();
                orderItems.add(orderItem);
            }
            order.setOrderDate(LocalDateTime.now());
            order.setOrderItems(orderItems);
            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.PENDING);
            orderRepository.save(order);
            cartService.deleteCart(cartId);
        }

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        User user=userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }
}
