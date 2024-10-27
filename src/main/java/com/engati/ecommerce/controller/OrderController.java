package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.entity.CartItem;
import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.request.OrderState;
import com.engati.ecommerce.responses.CartResponse;
import com.engati.ecommerce.responses.OrderResponse;
import com.engati.ecommerce.service.CartService;
import com.engati.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/buy/{userId}")
    public ResponseEntity<String> createOrder(@PathVariable Long userId) {
        try {
            List<CartItem> cartItems=cartService.cartItemofUser(userId);
           Long cartId= cartService.getCartId(userId);
            orderService.createOrderForUser(userId,cartItems,cartId);
            return ResponseEntity.ok("Order created successfully.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order.");
        }
    }


//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
//        List<Order> orders = orderService.getOrdersByUserId(userId);
//        System.out.println(orders);
//
//        List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::new).collect(Collectors.toList());
//        return ResponseEntity.ok(orderResponses);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);

        // Map each order to OrderResponse
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("merchant/{merchantId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByMerchantId(@PathVariable Long merchantId) {
        List<Order> orders = orderService.getOrdersOfMerchant(merchantId);

        // Map each order to OrderResponse
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("merchant/setstatus/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderState status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

}
