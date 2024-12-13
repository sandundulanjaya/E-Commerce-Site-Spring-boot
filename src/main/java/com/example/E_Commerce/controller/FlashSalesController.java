package com.example.E_Commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.E_Commerce.entity.FlashSales;
import com.example.E_Commerce.service.FlashSaleService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/flashsales")
public class FlashSalesController {

    @Autowired
    private FlashSaleService flashSaleService;

    @PostMapping
    public ResponseEntity<FlashSales> createFlashSale(@RequestBody FlashSales flashSale) {
        FlashSales createdFlashSale = flashSaleService.createFlashSale(flashSale);
        return ResponseEntity.ok(createdFlashSale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashSales> getFlashSaleById(@PathVariable String id) {
        Optional<FlashSales> flashSale = flashSaleService.getFlashSaleById(id);
        return flashSale.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<FlashSales>> getFlashSalesByProductId(@PathVariable String productId) {
        List<FlashSales> flashSales = flashSaleService.getFlashSalesByProductId(productId);
        return ResponseEntity.ok(flashSales);
    }

    @GetMapping
    public ResponseEntity<List<FlashSales>> getAllFlashSales() {
        List<FlashSales> flashSales = flashSaleService.getAllFlashSales();
        return ResponseEntity.ok(flashSales);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashSale(@PathVariable String id) {
        flashSaleService.deleteFlashSale(id);
        return ResponseEntity.noContent().build();
    }
}
