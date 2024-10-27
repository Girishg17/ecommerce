package com.engati.ecommerce.controller;


import com.engati.ecommerce.model.entity.ProductDocument;
import com.engati.ecommerce.request.ProdReq;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import com.engati.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<AllProductRes> findAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping("/category/{categoryId}")
    public List<AllProductRes>getProductByCategory(@PathVariable Long categoryId){
        return productService.getProductByCategory(categoryId);
    }

    @DeleteMapping("/delete/elastic")
    public void deleteP(){
        productService.deleteAllProducts();
    }

    @GetMapping("/{merchantId}/products")
    public ResponseEntity<List<ProdResponse>> getAllProductsOfMerchant(@PathVariable Long merchantId) {
        List<ProdResponse> products = productService.getAllProductOfMerchant(merchantId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }



    @PostMapping("/update/product/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId,
          ProdReq p
            ) throws IOException {

         productService.updateProduct(productId, p);
        return ResponseEntity.ok("updated success");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDocument>> searchProducts(@RequestParam String name) {
        List<ProductDocument> products = productService.searchProducts(name);
        return ResponseEntity.ok(products);
    }
    @PostMapping("/setrating/{productId}")
    public  ResponseEntity<String>updateRating(@PathVariable Long productId,@RequestParam("rating") double rating){
        productService.updateProductRating(productId,rating);
        return ResponseEntity.ok("updated success");
    }

}
