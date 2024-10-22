package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.dto.UserDto;
import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.model.entity.User;
import com.engati.ecommerce.model.enums.Role;
import com.engati.ecommerce.repository.MerchantRepository;
import com.engati.ecommerce.repository.UserRepository;
import com.engati.ecommerce.request.LoginCredentials;
import com.engati.ecommerce.responses.UserResponse;
import com.engati.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    String message;

//    @Override
//    @Transactional
//    public UserResponse registerUser(UserDto userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//
//        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
//        User savedUser = userRepository.save(user);
//
//        if (Role.valueOf(userDTO.getRole().toUpperCase()) == Role.MERCHANT) {
//            Merchant merchant = modelMapper.map(userDTO, Merchant.class);
//            merchant.setUser(savedUser);
//            merchant.setRating(0.0);
//
//            merchantRepository.save(merchant);
//            message = "Merchant Created Successfully";
//            return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
//        }
//        message = "User Created Successfully";
//        return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
//    }

    @Override
    @Transactional
    public UserResponse registerUser(UserDto userDTO) {
        try {
            // Map userDTO to User entity
            Optional<User> isUserExist=userRepository.findByEmail(userDTO.getEmail());
            if (isUserExist.isPresent()) {
                // Return a response indicating that the user already exists
                String errorMessage = "User already exists with this email";
                return new UserResponse(null, errorMessage, null);
            }
            User user = modelMapper.map(userDTO, User.class);
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));

            // Try saving the user
            User savedUser = userRepository.save(user);

            // Handle the case where the role is MERCHANT
            if (Role.valueOf(userDTO.getRole().toUpperCase()) == Role.MERCHANT) {
                Merchant merchant = modelMapper.map(userDTO, Merchant.class);
                merchant.setUser(savedUser);
                merchant.setRating(0.0);
                merchantRepository.save(merchant);
                message = "Merchant Created Successfully";
                return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
            }

            // Default case for other roles
            message = "User Created Successfully";
            return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());

        } catch (DataIntegrityViolationException ex) {
            // Handle the case where the email already exists (constraint violation)
            String errorMessage = "User already exists with this email";
            return new UserResponse(null, errorMessage, null);

        } catch (Exception ex) {
            // Handle any other exceptions
            String errorMessage = "An error occurred while registering the user";
            return new UserResponse(null, errorMessage, null);
        }
    }

    @Override
    public UserResponse loginUser(LoginCredentials loginCredentials) {
        User user = userRepository.findByEmail(loginCredentials.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginCredentials.getEmail()));

        if (!user.getPassword().equals(loginCredentials.getPassword())) {
            throw new RuntimeException("Invalid password provided.");
        }

        return new UserResponse(user.getId(), "Login successful", user.getRole().name());
    }

}
