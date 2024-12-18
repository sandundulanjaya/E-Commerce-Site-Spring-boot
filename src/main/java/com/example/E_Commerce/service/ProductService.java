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

    public Product getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check for active flash sales
        List<FlashSales> flashSales = flashSalesRepository.findByProductId(productId);
        Date now = new Date();
        product.setDiscountedPrice(product.getPrice());
        for (FlashSales flashSale : flashSales) {
            if (flashSale.getStartDate().before(now) && flashSale.getEndDate().after(now)) {
                product.setDiscountedPrice(flashSale.getDiscountedPrice());
                break;
            }
        }

        return product;
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
