package com.example.E_Commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_Commerce.entity.Review;
import com.example.E_Commerce.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(String productId, String userId, String comment,Float rating, String title) {
        reviewRepository.save(Review.builder()
                .productId(productId)
                .userId(userId)
                .comment(comment)
                .rating(rating).title(title).build());
    }

    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }
}
