package com.example.E_Commerce.service;

import com.example.E_Commerce.entity.Cart;
import com.example.E_Commerce.repository.CartRepository;
import com.example.E_Commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(String id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(String id) {
        cartRepository.deleteById(id);
    }

    public void deleteCartByUserId(String userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            cartRepository.delete(optionalCart.get());
        } else {
            throw new RuntimeException("Cart not found for user: " + userId);
        }
    }

    public Cart addProductToCart(String userId, String productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductIds(new ArrayList<>());
            cart.setProductQuantities(new HashMap<>());
        }
        if (!cart.getProductIds().contains(productId)) {
            cart.getProductIds().add(productId);
        }
        cart.getProductQuantities().put(productId, cart.getProductQuantities().getOrDefault(productId, 0) + quantity);
        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(String userId, String productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getProductIds().remove(productId);
            cart.getProductQuantities().remove(productId);
            updateTotalPrice(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("Cart not found for user: " + userId);
    }

    public Cart updateProductQuantity(String userId, String productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            if (quantity <= 0) {
                cart.getProductIds().remove(productId);
                cart.getProductQuantities().remove(productId);
            } else {
                if (!cart.getProductIds().contains(productId)) {
                    cart.getProductIds().add(productId);
                }
                cart.getProductQuantities().put(productId, quantity);
            }
            updateTotalPrice(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("Cart not found for user: " + userId);
    }

    private void updateTotalPrice(Cart cart) {
        double totalPrice = cart.getProductQuantities().entrySet().stream()
                .map(entry -> productRepository.findById(entry.getKey()).orElseThrow(() -> new RuntimeException("Product not found")))
                .mapToDouble(product -> product.getPrice().doubleValue() * cart.getProductQuantities().get(product.getId()))
                .sum();
        cart.setTotalPrice(totalPrice);
    }
}
