package com.example.E_Commerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_Commerce.entity.WishList;
import com.example.E_Commerce.repository.WishListRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public WishList addProductToWishList(String userId, String productId) {
        Optional<WishList> optionalWishList = wishListRepository.findByUserId(userId);
        WishList wishList;
        if (optionalWishList.isPresent()) {
            wishList = optionalWishList.get();
        } else {
            wishList = new WishList();
            wishList.setUserId(userId);
            wishList.setCreatedDate(new Date());
            wishList.setProductIds(new ArrayList<>());
        }
        if (wishList.getProductIds() == null) {
            wishList.setProductIds(new ArrayList<>()); 
        }
        if (wishList.getProductIds().contains(productId)) {
            throw new IllegalArgumentException("Product ID already exists in the wishlist");
        }
        wishList.getProductIds().add(productId);
        return wishListRepository.save(wishList);
    }

    public WishList removeProductFromWishList(String userId, String productId) {
        Optional<WishList> optionalWishList = wishListRepository.findByUserId(userId);
        if (optionalWishList.isPresent()) {
            WishList wishList = optionalWishList.get();
            wishList.getProductIds().remove(productId);
            return wishListRepository.save(wishList);
        }
        return null;
    }

    public WishList getWishListByUserId(String userId) {
        return wishListRepository.findByUserId(userId).orElse(null);
    }
}
