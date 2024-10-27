package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.*;
import com.engati.ecommerce.repository.*;
import com.engati.ecommerce.request.ProdReq;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import com.engati.ecommerce.service.CloudinaryService;
import com.engati.ecommerce.service.MerchantService;
import com.engati.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void addproduct(Long merchantId, ProductDto pdto) {

        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Category category = categoryRepository.findById(pdto.getCategoryId())  // <-- Handle Category
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = modelMapper.map(pdto, Product.class);
        product.setMerchant(merchant);
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<AllProductRes> getAllProduct() {
        List<Product> products = productRepository.findAllByDeletedFalse();
        List<AllProductRes> allProductResponses = new ArrayList<>();
        for (Product product : products) {
            AllProductRes response = modelMapper.map(product, AllProductRes.class);
            response.setCategory(product.getCategory().getName());
            allProductResponses.add(response);
        }

        return allProductResponses;
    }

    @Override
    public List<ProdResponse> getAllProductOfMerchant(Long merchantId) {
        List<Product> products = productRepository.findByMerchantIdAndDeletedFalse(merchantId);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ProdResponse convertToResponse(Product product) {
        return modelMapper.map(product, ProdResponse.class);
    }

    @Override
    public void addproductswithCloudinary(ProductRequest productRequest, Long merchantId) throws IOException {
        String imageUrl = cloudinaryService.upload(productRequest.getImage());
        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Product product = new Product();

        product.setName(productRequest.getName());
        product.setUsp(productRequest.getUsp());
        product.setDescription(productRequest.getDescription());
        product.setFile(imageUrl);
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setMerchant(merchant);
        product.setCategory(category);
        product.setRating(0.0);
        product.setRatingCount(0);
        Product saved = productRepository.save(product);
        indexProductInElasticsearch(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        product.setDeleted(true);
        productRepository.save(product);
        productSearchRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Long id, ProdReq p) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (p.getImage() != null && !p.getImage().isEmpty()) {
            String imageUrl = cloudinaryService.upload(p.getImage());
            existingProduct.setFile(imageUrl);
        }
        existingProduct.setName(p.getName());
        existingProduct.setPrice(p.getPrice());
        existingProduct.setStock(p.getStock());


        Product existing = productRepository.save(existingProduct);
        indexProductInElasticsearch(existing);
    }

    public void updateStockofProduct(Product p) {
        Product existingProduct = productRepository.findById(p.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setStock(p.getStock());
        Product existing = productRepository.save(existingProduct);
        indexProductInElasticsearch(existing);
    }

    @Override
    public List<AllProductRes> getProductByCategory(Long Id) {
        Category cat = categoryRepository.findById(Id).orElseThrow(() -> new RuntimeException("Product not found"));
        List<Product> products = productRepository.findAllByCategoryAndDeletedFalse(cat);

        List<AllProductRes> allProductResponses = new ArrayList<>();
        for (Product product : products) {
            AllProductRes response = modelMapper.map(product, AllProductRes.class);
            response.setCategory(product.getCategory().getName());

            allProductResponses.add(response);
        }

        return allProductResponses;
    }

    private void indexProductInElasticsearch(Product product) {
        Merchant merchant = merchantRepository.findById(product.getMerchant().getId()).orElseThrow();

        ProductDocument document = new ProductDocument();
        document.setId(product.getId());
        document.setName(product.getName());
        document.setDescription(product.getDescription());
        document.setPrice(product.getPrice());
        document.setStock(product.getStock());
        document.setFile(product.getFile());
        document.setMerchantId(merchant.getId());
        document.setMerchantName(merchant.getUser().getName());
        document.setProductRating(product.getRating());
        document.setMerchantTotalProducts(merchant.getProducts().size());
        document.setMerchantTotalOrders(getTotalOrdersForMerchant(merchant.getId()));
        document.setRatingCount(product.getRatingCount());


        productSearchRepository.save(document);
    }

    private int getTotalOrdersForMerchant(Long merchantId) {
        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        return orderRepository.findByMerchant(merchant).size(); // Example value
    }



    public List<ProductDocument> searchProducts(String name) {
        List<ProductDocument> products = productSearchRepository.findByNameContaining(name);
        products.sort(Comparator.comparing(this::calculateWeightedScore).reversed());
        return products;
    }

    private double calculateWeightedScore(ProductDocument product) {
        double stockWeight = 0.3;
        double ratingWeight = 0.2;
        double priceWeight = 0.1;
        double totalOrdersWeight = 0.4;

        double score = (product.getStock() * stockWeight) +
                (product.getProductRating() * ratingWeight) +
                (product.getPrice() * priceWeight) +
                (product.getMerchantTotalOrders() * totalOrdersWeight);
        System.out.println("score" + product.getMerchantName() + score);
        return score;
    }

    public void deleteAllProducts() {
        productSearchRepository.deleteAll();
    }

    @Override
    public void updateProductRating(Long ProductId, double rating) {
        Product existingProduct = productRepository.findById(ProductId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Integer currentRatingCount = existingProduct.getRatingCount();
        Double currentRating = existingProduct.getRating();
        double newRating = calculateNewRating(rating, currentRatingCount, currentRating);
        existingProduct.setRating(newRating);
        existingProduct.setRatingCount(existingProduct.getRatingCount() + 1);
        Product existing = productRepository.save(existingProduct);
        indexProductInElasticsearch(existing);

    }

    private double calculateNewRating(double rating, int currentRatingCount, double currentRating) {
        double newRating = ((currentRating * currentRatingCount) + rating) / (currentRatingCount + 1);
        newRating = Math.min(5.0, Math.round(newRating * 10) / 10.0);
        return newRating;
    }

}




