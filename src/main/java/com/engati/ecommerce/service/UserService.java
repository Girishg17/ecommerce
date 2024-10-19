package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.UserDto;
import com.engati.ecommerce.responses.UserResponse;

public interface UserService {
    UserResponse registerUser(UserDto userDTO);
    UserDto loginUser(String email,String password);
}
