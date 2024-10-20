package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.service.MerchantService;
import com.engati.ecommerce.service.ProductService;
import com.engati.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/service/merchant")
public class MerchantController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

        @PostMapping("/{merchantId}/upload")
    public ResponseEntity<String> uploadProduct(
                @PathVariable Long merchantId,
                @ModelAttribute ProductDto productDto
                ){
            productService.addproduct(merchantId,productDto);

            return null;
        }



    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


}
