package com.example.E_Commerce.service;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.E_Commerce.Enums.Role;
import com.example.E_Commerce.entity.Address;
import com.example.E_Commerce.entity.User;
import com.example.E_Commerce.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
   
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String firstName, String lastName, String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(role)
                .address(Address.createEmpty())  // Initialize with empty address
                .build();

        return userRepository.save(user);
    }

    public User updateProfile(String userId, String firstName, String lastName, Address address, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        
        // Only update password if a new one is provided
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
        }
        
        return userRepository.save(user);
    }
    
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
