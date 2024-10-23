package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.dto.CartDTO;
import com.engati.ecommerce.model.dto.CartItemDto;
import com.engati.ecommerce.model.entity.Cart;
import com.engati.ecommerce.model.entity.CartItem;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.model.entity.User;
import com.engati.ecommerce.repository.CartItemRepository;
import com.engati.ecommerce.repository.CartRepository;
import com.engati.ecommerce.repository.ProductRepository;
import com.engati.ecommerce.repository.UserRepository;
import com.engati.ecommerce.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CartDTO getCartByUserId(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        CartDTO cartDto=modelMapper.map(cartOptional,CartDTO.class);
        return cartDto;
    }

    public void addToCart(Long userId, CartItemDto cartItemDTO) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cartRepository.save(cart);
    }

    private Cart createNewCart(Long userId) {
        Cart cart = new Cart();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public void removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    public void updateCartItem(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
}
