package com.example.E_Commerce.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.E_Commerce.entity.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
  
    List<Review> findByProductId(String productId);

    
} 
