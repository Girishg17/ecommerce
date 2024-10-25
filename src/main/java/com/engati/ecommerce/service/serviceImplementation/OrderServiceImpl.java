package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.entity.*;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.model.enums.Role;
import com.engati.ecommerce.repository.OrderRepository;
import com.engati.ecommerce.repository.ProductRepository;
import com.engati.ecommerce.repository.ProductSearchRepository;
import com.engati.ecommerce.repository.UserRepository;
import com.engati.ecommerce.service.*;
//import com.engati.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Autowired
    private CartService cartService;

    @Override
    public void createOrderForUser(Long userId, List<CartItem> cartItems, Long cartId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Map<Merchant, List<CartItem>> itemsGroupedByMerchant = cartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getMerchant()));
        for (Map.Entry<Merchant, List<CartItem>> entry : itemsGroupedByMerchant.entrySet()) {
            Merchant merchant = entry.getKey();
            List<CartItem> merchantItems = entry.getValue();
            Order order = new Order();
            order.setUser(user);
            order.setMerchant(merchant);

            List<OrderItem> orderItems = new ArrayList<>();
            Double totalAmount = 0.0;
            for (CartItem cartItem : merchantItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice());

                totalAmount += cartItem.getQuantity() * cartItem.getProduct().getPrice();
                orderItems.add(orderItem);
                Product product = cartItem.getProduct();
                int newStock = product.getStock() - cartItem.getQuantity();
                if (newStock < 0) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                product.setStock(newStock);
                // Save the updated product to the repository
                productService.updateStockofProduct(product);

            }
            order.setOrderDate(LocalDateTime.now());
            order.setOrderItems(orderItems);
            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.PENDING);
            orderRepository.save(order);
            incrementMerchantOrdersInElasticsearch(merchant.getId());
            cartService.deleteCart(cartId);


//
//            emailService.sendEmail(user.getEmail(),
//                    "Confirmation of Your Order #" + order.getId(),
//                    "Your order has been placed successfully.");
//
        }

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }


    private void incrementMerchantOrdersInElasticsearch(Long merchantId) {
        // Retrieve all ProductDocuments for the given merchant
        List<ProductDocument> merchantProducts = productSearchRepository.findAllByMerchantId(merchantId);

        // Increment the merchantTotalOrders for each product document
        for (ProductDocument product : merchantProducts) {
            product.setMerchantTotalOrders(product.getMerchantTotalOrders() + 1);
        }

        // Save updated product documents back to Elasticsearch
        productSearchRepository.saveAll(merchantProducts);
    }
}
