package com.example.E_Commerce.controller;

import com.example.E_Commerce.entity.Product;
import com.example.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

   
    @Autowired
    private ProductService productService;

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable String productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer quantityInStock,
            @RequestParam(required = false) String imageUrl1,
            @RequestParam(required = false) String imageUrl2,
            @RequestParam(required = false) String imageUrl3,
            @RequestParam(required = false) String imageUrl4,
            @RequestParam(required = false) String imageUrl5,
            @RequestParam(required = false) Float rating,
            @RequestParam(required = false) String category) {

        try {
            productService.updateProduct(productId, name, description, price, quantityInStock, imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5, rating ,category);
            return ResponseEntity.ok("Product updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Product not found");
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addProduct(
            @RequestBody Product product) {

        productService.addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getDiscountedPrice(),  product.getQuantityInStock(), product.getImageUrl1(), product.getImageUrl2(), product.getImageUrl3(), product.getImageUrl4(), product.getImageUrl5(), product.getRating(), product.getCategory());
        return ResponseEntity.ok("Product added successfully");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Product not found");
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
