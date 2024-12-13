package com.example.E_Commerce.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Document("wishlists")
@AllArgsConstructor
@NoArgsConstructor
public class WishList {
    @Id
    private String id;
    private String userId;
    private List<String> productIds;
    private Date createdDate;
   
}
