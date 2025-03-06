package com.example.E_Commerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.entity.Product;
import com.example.E_Commerce.repository.OrderRepository;
import com.example.E_Commerce.repository.ProductRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public String createOrder(Order order) {
        order.setOrderDate(new Date());
        order.setStatus("PROCESSING");
        BigDecimal totalPrice = calculateTotalPrice(order);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order.getId();
    }

    private BigDecimal calculateTotalPrice(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : order.getProductQuantities().entrySet()) {
            String productId = entry.getKey();
            Integer quantity = entry.getValue();
            BigDecimal productPrice = getProductPriceById(productId);
            totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        return totalPrice;
    }

    private BigDecimal getProductPriceById(String productId) {
        return productRepository.findById(productId)
                .map(product -> product.getPrice())
                .orElse(BigDecimal.ZERO);
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public void changeOrderStatus(String orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public BigDecimal getTotalSales() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getStatus().equals("COMPLETED"))
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer getTotalOrders() {
        List<Order> orders = orderRepository.findAll();
        return (int) orders.stream().filter(order -> order.getStatus().equals("COMPLETED")).count();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));
    }
}