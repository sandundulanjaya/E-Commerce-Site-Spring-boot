package com.example.E_Commerce.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.E_Commerce.entity.Product;

@Component
public class SearchRepositoryImpl implements SerachRepository {

    @Override
    public List<Product> findByText(String text) {

        List<Product> products = new ArrayList<Product>();

        
        return products;
    }
    
}
