package com.engati.ecommerce.controller;

import com.cloudinary.api.ApiResponse;
import com.engati.ecommerce.model.dto.UserDto;
import com.engati.ecommerce.model.entity.User;
import com.engati.ecommerce.request.LoginCredentials;
import com.engati.ecommerce.responses.UserResponse;
import com.engati.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
//        try {
//            // Registration logic
//            User newUser = userService.registerUser(userDto);
//            return ResponseEntity.ok(new ApiResponse("User registered successfully", newUser.getId(), newUser.getRole()));
//        } catch (SQLIntegrityConstraintViolationException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User already exists", null, null));
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong", null, null));
//        }
//    }

    @PostMapping("/login")
    public UserResponse loginUser(@RequestBody LoginCredentials loginCredentials) {
        return userService.loginUser(loginCredentials);
    }


}
