package com.example.E_Commerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.E_Commerce.entity.WishList;

import java.util.Optional;

public interface WishListRepository extends MongoRepository<WishList, String> {
    Optional<WishList> findByUserId(String userId);
}
