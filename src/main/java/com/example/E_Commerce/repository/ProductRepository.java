package com.example.E_Commerce.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.E_Commerce.entity.Product;

public interface ProductRepository extends  MongoRepository<Product, String> {
    List<Product> findByCategory(String category);

   
}

