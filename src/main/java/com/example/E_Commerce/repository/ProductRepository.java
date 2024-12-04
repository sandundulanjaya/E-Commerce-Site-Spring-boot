package com.example.E_Commerce.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.E_Commerce.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
