package com.example.E_Commerce.controller;

import com.example.E_Commerce.entity.Product;
import com.example.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

   
    @Autowired
    private ProductService productService;
    

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        productService.updateProduct(productId, product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            //product.setPrice(product.getDiscountedPrice() == null ? product.getPrice() : product.getDiscountedPrice());
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

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> searchMovies(@PathVariable String name) {
        List<Product> movies = productService.searchProductsByName(name);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/productcount")
    public Integer getProductCount() {
        return productService.getProductCount();
    }
    

    
    
}
