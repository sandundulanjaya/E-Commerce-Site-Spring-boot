package com.example.E_Commerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.E_Commerce.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;
    
    private Role role;
}
