package com.engati.ecommerce.request;

import com.engati.ecommerce.model.enums.OrderStatus;

public class OrderState {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = OrderStatus.valueOf(status);
    }
}
