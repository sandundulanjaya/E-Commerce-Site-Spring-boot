package com.example.E_Commerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Document("reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Review {
    @Id
    private String id;
    private String userId;
    private String productId;
    private String title;
    private String rating;
    private String comment;
}
