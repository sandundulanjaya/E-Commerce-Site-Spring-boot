package com.example.E_Commerce.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "carts")
public class Cart {
   
    @Id
    private String id;
    private List<String> productIds;
    private double totalPrice;
    private String userId; 
    private Map<String, Integer> productQuantities;
 
}
