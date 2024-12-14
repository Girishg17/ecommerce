package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.CartItDto;
import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.request.OrderState;
import com.engati.ecommerce.responses.OrderResponse;

import com.engati.ecommerce.service.CategoryService;
import com.engati.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/buy/{userId}")
    public ResponseEntity<String> createOrder(@PathVariable Long userId) {
        try {
            List<CartItDto> cartItems = getCartItemsOfUser(userId);//should
            Long cartId = getCartIdOfUser(userId);//should
            orderService.createOrderForUser(userId, cartItems, cartId);
            return ResponseEntity.ok("Order created successfully.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order.");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> new OrderResponse(order, categoryService))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByMerchantId(@PathVariable Long merchantId) {
        List<Order> orders = orderService.getOrdersOfMerchant(merchantId);
        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> new OrderResponse(order, categoryService))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("merchant/setstatus/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderState status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/count/{merchantId}")
    public ResponseEntity<Integer> getOrderCountByMerchant(@PathVariable Long merchantId) {
        int orderCount = orderService.getOrdersOfMerchant(merchantId).size();
        return ResponseEntity.ok(orderCount);
    }

    private List<CartItDto> getCartItemsOfUser(Long userId) {
        System.out.println("userId" + userId);
        String url = "http://localhost:8084/api/cart/items/" + userId;

        ResponseEntity<List<CartItDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CartItDto>>() {
                }
        );
        System.out.println("response body" + response.getBody().get(0).getProductId());
        return response.getBody();
    }

    private Long getCartIdOfUser(Long userId) {
        String url = "http://localhost:8084/api/cart/id/" + userId;
        ResponseEntity<Long> response = restTemplate.getForEntity(url, Long.class);
        return response.getBody();
    }

    @PostMapping("/rating/update/{productId}")
    public void updateRatinginCart(@PathVariable Long productId, @RequestBody Map<String, Double> request) {
        Double rating = request.get("rating");
        if (rating == null) {
            throw new IllegalArgumentException("Rating is required in the request body.");
        }

        orderService.updateCartItemRatings(productId, rating);

        System.out.println("Product and cart item ratings updated successfully.");
    }

}
