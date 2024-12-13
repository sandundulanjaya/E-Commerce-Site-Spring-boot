package com.example.E_Commerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.E_Commerce.entity.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}