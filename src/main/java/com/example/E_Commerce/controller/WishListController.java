package com.example.E_Commerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.E_Commerce.entity.WishList;
import com.example.E_Commerce.service.WishListService;

@CrossOrigin
@RestController
@RequestMapping("api/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/add/{userId}/{productId}")
    public ResponseEntity<?> addProductToWishList(@PathVariable String userId, @PathVariable String productId) {
        try {
            WishList wishList = wishListService.addProductToWishList(userId, productId);
            return ResponseEntity.ok(wishList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public WishList removeProductFromWishList(@PathVariable String userId, @PathVariable String productId) {
        return wishListService.removeProductFromWishList(userId, productId);
    }

    @GetMapping("/{userId}")
    public WishList getWishListByUserId(@PathVariable String userId) {
        return wishListService.getWishListByUserId(userId);
    }
}
