package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.UserDto;
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

//        return  new UserResponse(12233L,"he;llo",userDto.getRole());
        return userService.registerUser(userDto);
    }




}
