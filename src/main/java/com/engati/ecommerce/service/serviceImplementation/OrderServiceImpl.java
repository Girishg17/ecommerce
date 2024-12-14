package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.dto.CartItDto;
import com.engati.ecommerce.model.dto.ProductsDto;
import com.engati.ecommerce.model.entity.*;
import com.engati.ecommerce.model.enums.OrderStatus;
import com.engati.ecommerce.repository.OrderItemRepository;
import com.engati.ecommerce.repository.OrderRepository;
import com.engati.ecommerce.request.OrderState;
import com.engati.ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private MerchantService merchantService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void createOrderForUser(Long userId, List<CartItDto> cartItems, Long cartId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Map<Merchant, List<CartItDto>> itemsGroupedByMerchant = new HashMap<>();

        for (CartItDto cartItem : cartItems) {
            String productUrl = "http://localhost:8083/product/products/" + cartItem.getProductId();
            ProductsDto product = restTemplate.getForObject(productUrl, ProductsDto.class);

            if (product != null) {
                Long merchantId = product.getMerchantId();
                Merchant merchants = merchantService.findMerchantById(merchantId)
                        .orElseThrow(() -> new RuntimeException("Merchant not found"));

                itemsGroupedByMerchant
                        .computeIfAbsent(merchants, k -> new ArrayList<>())
                        .add(cartItem);
            }
        }
        for (Map.Entry<Merchant, List<CartItDto>> entry : itemsGroupedByMerchant.entrySet()) {
            Merchant merchant = entry.getKey();
            List<CartItDto> merchantItems = entry.getValue();
            Order order = new Order();
            order.setUser(user);
            order.setMerchant(merchant);

            List<OrderItem> orderItems = new ArrayList<>();
            Double totalAmount = 0.0;
            for (CartItDto cartItem : merchantItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                String productUrl = "http://localhost:8083/product/products/" + cartItem.getProductId();
                ProductsDto product = restTemplate.getForObject(productUrl, ProductsDto.class);
                orderItem.setProductId(product.getId());
                orderItem.setFile(product.getFile());
                orderItem.setName(product.getName());
               // orderItem.setProductprice(product.getPrice());
                orderItem.setStock(product.getStock());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setUsp(product.getUsp());
                orderItem.setDescription(product.getDescription());
                orderItem.setRating(product.getRating());
                orderItem.setRatingCount(product.getRatingCount());
                totalAmount += cartItem.getQuantity() * product.getPrice();
                orderItems.add(orderItem);

                int newStock = product.getStock() - cartItem.getQuantity();
                if (newStock < 0) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                product.setStock(newStock);


                String updateStockUrl = "http://localhost:8083/product/" + product.getId() + "/stock";
                restTemplate.put(updateStockUrl, product);

            }
            order.setOrderDate(LocalDateTime.now());
            order.setOrderItems(orderItems);
            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.PENDING);
            orderRepository.save(order);
            String url = "http://localhost:8085/products/search/increment/" + merchant.getId();
            restTemplate.postForEntity(url, null, Void.class);

            String cartUrl="http://localhost:8084/api/cart/delete/"+cartId;
            restTemplate.exchange(
                    cartUrl,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
           // cartService.deleteCart(cartId);


//            emailService.sendEmail(user.getEmail(),
//                    "Confirmation of Your Order #" + order.getId(),
//                    "Your order has been placed successfully.");
        }

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getOrdersOfMerchant(Long merchantId){
        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
         return orderRepository.findByMerchant(merchant);
    }



    public Order updateOrderStatus(Long orderId, OrderState status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status.getStatus());
        return orderRepository.save(order);
    }

    public void updateCartItemRatings(Long productId, double newRating) {
        List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);

        for (OrderItem orderItem : orderItems) {
            orderItem.setRating(newRating);
            orderItem.setRatingCount(orderItem.getRatingCount()+1);
        }

        orderItemRepository.saveAll(orderItems);
    }
}
