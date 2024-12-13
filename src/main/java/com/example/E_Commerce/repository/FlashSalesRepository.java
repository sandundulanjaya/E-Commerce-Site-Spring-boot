package com.example.E_Commerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.E_Commerce.entity.FlashSales;

import java.util.List;

public interface FlashSalesRepository extends MongoRepository<FlashSales, String> {
    List<FlashSales> findByProductId(String productId);
}
