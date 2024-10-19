package com.engati.ecommerce.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse{
    private Long id;
    private String message;
    private String role;

    public UserResponse(Long id, String message, String role) {
        this.id = id;
        this.message = message;
        this.role = role;
    }
}
