package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.UserDto;
import com.engati.ecommerce.model.entity.User;
import com.engati.ecommerce.request.LoginCredentials;
import com.engati.ecommerce.responses.UserResponse;
import java.util.Optional;

public interface UserService {
    UserResponse registerUser(UserDto userDTO);
    UserResponse loginUser(LoginCredentials loginCredentials);
    public Optional<User> getUserById(Long id);
}
