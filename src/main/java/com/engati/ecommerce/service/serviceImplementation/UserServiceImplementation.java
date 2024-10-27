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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ModelMapper modelMapper;

    String message;

    @Override
    @Transactional
    public UserResponse registerUser(UserDto userDTO) {
        try {
            Optional<User> isUserExist = userRepository.findByEmail(userDTO.getEmail());
            if (isUserExist.isPresent()) {
                String errorMessage = "User already exists with this email";
                return new UserResponse(null, errorMessage, null);
            }

            User user = modelMapper.map(userDTO, User.class);
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());


            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));

            User savedUser = userRepository.save(user);

            if (Role.valueOf(userDTO.getRole().toUpperCase()) == Role.MERCHANT) {
                Merchant merchant = modelMapper.map(userDTO, Merchant.class);
                merchant.setUser(savedUser);

                merchantRepository.save(merchant);
                message = "Merchant Created Successfully";
                return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());
            }

            message = "User Created Successfully";
            return new UserResponse(savedUser.getId(), message, savedUser.getRole().name());

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = "User already exists with this email";
            return new UserResponse(null, errorMessage, null);

        } catch (Exception ex) {
            String errorMessage = "An error occurred while registering the user";
            return new UserResponse(null, errorMessage, null);
        }
    }

    @Override
    public UserResponse loginUser(LoginCredentials loginCredentials) {
        User user = userRepository.findByEmail(loginCredentials.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginCredentials.getEmail()));
        if (!BCrypt.checkpw(loginCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password provided.");
        }

        return new UserResponse(user.getId(), "Login successful", user.getRole().name());
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
}
