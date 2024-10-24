package com.engati.ecommerce.responses;

import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.model.entity.OrderItem;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("products")
    private List<Presponse> products;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    @JsonProperty("status")
    private OrderStatus status;

    // Constructor
    public OrderResponse(Order order) {
        this.orderId = order.getId();

        // Map each OrderItem to Presponse, including the quantity
        this.products = order.getOrderItems().stream()
                .map(orderItem -> new Presponse(orderItem.getProduct(), orderItem))
                .collect(Collectors.toList());

        this.totalPrice = calculateTotalPrice(order);
        this.status = order.getStatus();
    }

    // Method to calculate total price
    private Double calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Getters
    public Long getOrderId() {
        return orderId;
    }

    public List<Presponse> getProducts() {
        return products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
