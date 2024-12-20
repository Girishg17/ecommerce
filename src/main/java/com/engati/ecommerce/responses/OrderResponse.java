package com.engati.ecommerce.responses;

import com.engati.ecommerce.model.entity.Order;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

//    public OrderResponse(Order order, CategoryService categoryService) {
//        this.orderId = order.getId();
//
//        this.products = order.getOrderItems().stream()
//                .map(orderItem -> new Presponse(orderItem.getProduct(), orderItem,categoryService))
//                .collect(Collectors.toList());
//
//        this.totalPrice = calculateTotalPrice(order);
//        this.status = order.getStatus();
//        this.orderDate=order.getOrderDate();
//    }

    public OrderResponse(Order order, CategoryService categoryService) {
        this.orderId = order.getId();

        this.products = order.getOrderItems().stream()
                .map(orderItem -> new Presponse(

                        orderItem.getName(),
                        orderItem.getUsp(),
                        orderItem.getDescription(),
                        orderItem.getFile(),
                        orderItem.getPrice(), // if needed, or map to another source
                        orderItem.getStock(),
                        orderItem.getRating(),
                        orderItem.getRatingCount(),
                        orderItem.getMerchantId(),
                        orderItem.getCategoryId(),
                        orderItem.isDeleted(),
                        orderItem.getQuantity(),
                        orderItem.getProductId(),
                        categoryService))
                .collect(Collectors.toList());

        this.totalPrice = calculateTotalPrice(order);
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }


    private Double calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

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
