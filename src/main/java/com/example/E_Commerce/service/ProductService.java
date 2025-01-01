package com.example.E_Commerce.service;

import com.example.E_Commerce.entity.FlashSales;
import com.example.E_Commerce.entity.Product;
import com.example.E_Commerce.repository.FlashSalesRepository;
import com.example.E_Commerce.repository.ProductRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

   
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private FlashSalesRepository flashSalesRepository;

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    public void updateProduct(String productId, String name, String description, BigDecimal price,
            Integer quantityInStock, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4,
            String imageUrl5, Float rating, String category) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    if (name != null) {
                        product.setName(name);
                    }
                    if (description != null) {
                        product.setDescription(description);
                    }
                    if (price != null) {
                        product.setPrice(price);
                    }
                    if (quantityInStock != null) {
                        product.setQuantityInStock(quantityInStock);
                    }
                    if (imageUrl1 != null) {
                        product.setImageUrl1(imageUrl1);
                    }
                    if (imageUrl2 != null) {
                        product.setImageUrl2(imageUrl2);
                    }
                    if (imageUrl3 != null) {
                        product.setImageUrl3(imageUrl3);
                    }
                    if (imageUrl4 != null) {
                        product.setImageUrl4(imageUrl4);
                    }
                    if (imageUrl5 != null) {
                        product.setImageUrl5(imageUrl5);
                    }
                    if (rating != null) {
                        product.setRating(rating);
                    }
                    if (category != null) {
                        product.setCategory(category);

                    }

                    productRepository.save(product);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public void addProduct(String name, String description, BigDecimal price, BigDecimal discountedPrice ,Integer quantityInStock, String imageUrl1,
            String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, Float rating, String category) {
        productRepository
                .save(new Product(null, name, description, price, discountedPrice, quantityInStock, category, imageUrl1, imageUrl2,
                        imageUrl3, imageUrl4, imageUrl5, rating));
    }

    public void updateDiscountedPrice(String productId, BigDecimal discountedPrice) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    product.setDiscountedPrice(discountedPrice);
                    productRepository.save(product);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public void resetDiscountedPrice(String productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    product.setDiscountedPrice(null);
                    productRepository.save(product);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public Product getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<FlashSales> flashSales = flashSalesRepository.findByProductId(productId);
        Date now = new Date();
        
        // Reset to original price first
        product.setDiscountedPrice(product.getPrice());
        
        // Apply active flash sale discount if exists
        flashSales.stream()
                .filter(sale -> sale.getStartDate().before(now) && sale.getEndDate().after(now))
                .findFirst()
                .ifPresent(sale -> product.setDiscountedPrice(sale.getDiscountedPrice()));

        return product;
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

   
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
