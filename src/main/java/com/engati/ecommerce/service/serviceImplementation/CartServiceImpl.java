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
import com.engati.ecommerce.responses.CartItemResponse;
import com.engati.ecommerce.responses.CartResponse;
import com.engati.ecommerce.service.CartService;
import com.engati.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartResponse getCartByUserId(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        CartResponse cartResponse = new CartResponse();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItemResponse> itemResponses = new ArrayList<>();

            for (CartItem cartItem : cart.getItems()) {
                Product product = productService.getProductById(cartItem.getProduct().getId()); // Fetch the product

                // Create a new CartItemResponse for each cart item
                CartItemResponse itemResponse = new CartItemResponse();
                itemResponse.setProductId(product.getId());
                itemResponse.setProductName(product.getName());
                itemResponse.setProductUsp(product.getUsp());
                itemResponse.setProductDescription(product.getDescription());
                itemResponse.setProductFile(product.getFile());
                itemResponse.setProductPrice(product.getPrice());
                itemResponse.setProductStock(product.getStock());
                itemResponse.setQuantity(cartItem.getQuantity());

                itemResponses.add(itemResponse);
            }

            cartResponse.setItems(itemResponses); // Set the list of items in the cart response
        }

        return cartResponse; // Return the cart response
    }



//    public void addToCart(Long userId, CartItemDto cartItemDTO) {
//        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));
//        System.out.println("cart"+cart.getItems());
//        Product product = productRepository.findById(cartItemDTO.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        System.out.println("PR "+product.getName());
////
//        CartItem cartItem = new CartItem();
//        cartItem.setProduct(product);
//        cartItem.setQuantity(cartItemDTO.getQuantity());
//        cartItem.setCart(cart);
////
//        cart.getItems().add(cartItem);
//        cartRepository.save(cart);
//    }
public List<CartItem> cartItemofUser(Long userId) {
        List<CartItem>cart=cartRepository.findByUserId(userId).get().getItems();
        return cart;
}
public Long getCartId(Long userId){
        return cartRepository.findByUserId(userId).get().getId();
}
    public String addToCart(Long userId, CartItemDto cartItemDTO) {
        // Find or create a new cart for the user
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));
        if (cart == null) {
            return "Cart could not be created";
        }
        // Find the product by ID
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("PR " + product.getName());

        // Check if the product is already in the cart
        boolean productExistsInCart = cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()));

        if (productExistsInCart) {
            // If product already exists, return an error message
            return "Product already in cart";
        }

        // Create a new CartItem and set its properties
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setCart(cart);

        // Add the new CartItem to the cart and save
        cart.getItems().add(cartItem);
        cartRepository.save(cart);

        // Return success message
        return "Product added to cart successfully";
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
    public void deleteCart(Long cartId){

        cartRepository.deleteById(cartId);
        System.out.println("Deleted succces");
    }
}
