package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.UserDto;
import com.engati.ecommerce.request.LoginCredentials;
import com.engati.ecommerce.responses.UserResponse;
import com.engati.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserDto userDto) {

        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public UserResponse loginUser(@RequestBody LoginCredentials loginCredentials) {
        return userService.loginUser(loginCredentials);
    }


}
