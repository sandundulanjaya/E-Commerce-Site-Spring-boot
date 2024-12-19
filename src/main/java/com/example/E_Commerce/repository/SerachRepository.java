package com.example.E_Commerce.repository;

import java.util.List;

import com.example.E_Commerce.entity.Product;

public interface SerachRepository {

    List<Product> findByText(String text);
    
} 
