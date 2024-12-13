package com.example.E_Commerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Document("orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String userId;
    private List<String> productIds; // List of product IDs
    private Map<String, Integer> productQuantities; // Map of productId to quantity
    private BigDecimal totalPrice;
    private Date orderDate;
    private String status;
}