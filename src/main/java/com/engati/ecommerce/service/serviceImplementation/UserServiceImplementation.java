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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public UserResponse registerUser(UserDto userDTO) {
//        System.out.println("register User calling "+ userDTO.getRole()+ userDTO.getPassword()+userDTO.getName()+"email:->"+userDTO.getEmail());
        User user = modelMapper.map(userDTO, User.class);

        System.out.println("register User calling " + userDTO.getRole() + userDTO.getPassword() + userDTO.getName() + "email:->" + userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        User savedUser = userRepository.save(user);

        if (Role.valueOf(userDTO.getRole().toUpperCase()) == Role.MERCHANT) {
            Merchant merchant = modelMapper.map(userDTO, Merchant.class);
            merchant.setUser(savedUser);
            merchant.setRating(0.0);

            merchantRepository.save(merchant);
            message = "Merchant Created Successfully";

            return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
        }

        message = "User Created Successfully";
        return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
    }

    @Override
    public UserResponse loginUser(LoginCredentials loginCredentials) {
        User user = userRepository.findByEmail(loginCredentials.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginCredentials.getEmail()));

        if (!user.getPassword().equals(loginCredentials.getPassword())) {
            throw new RuntimeException("Invalid password provided.");
        }

        return new UserResponse(user.getId(), "Login successful",user.getRole().name());
    }

}
