package com.example.E_Commerce.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.E_Commerce.entity.User;

public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByEmail(String email);
}
