package com.example.E_Commerce.controller;

import com.example.E_Commerce.entity.Cart;
import com.example.E_Commerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCartById(@PathVariable String id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable String userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable String id) {
        cartService.deleteCart(id);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteCartByUserId(@PathVariable String userId) {
        cartService.deleteCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable String userId, @PathVariable String productId, @PathVariable(required = false) Integer quantity) {
        if (quantity == null) {
            quantity = 1;
        }
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable String userId, @PathVariable String productId) {
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{userId}/{productId}/{quantity}")
    public ResponseEntity<Cart> updateProductQuantity(@PathVariable String userId, @PathVariable String productId, @PathVariable int quantity) {
        Cart cart = cartService.updateProductQuantity(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
}
