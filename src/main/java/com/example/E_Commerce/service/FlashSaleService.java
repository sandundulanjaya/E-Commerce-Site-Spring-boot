package com.example.E_Commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_Commerce.entity.FlashSales;
import com.example.E_Commerce.repository.FlashSalesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FlashSaleService {

    @Autowired
    private FlashSalesRepository flashSalesRepository;
    
    @Autowired
    private ProductService productService;

    public FlashSales createFlashSale(FlashSales flashSale) {
        FlashSales savedFlashSale = flashSalesRepository.save(flashSale);
        updateProductDiscountedPrice(savedFlashSale);
        return savedFlashSale;
    }

    private void updateProductDiscountedPrice(FlashSales flashSale) {
        productService.updateDiscountedPrice(flashSale.getProductId(), flashSale.getDiscountedPrice());
    }

    public Optional<FlashSales> getFlashSaleById(String id) {
        return flashSalesRepository.findById(id);
    }

    public List<FlashSales> getFlashSalesByProductId(String productId) {
        return flashSalesRepository.findByProductId(productId);
    }

    public List<FlashSales> getAllFlashSales() {
        return flashSalesRepository.findAll();
    }

    public void deleteFlashSale(String id) {
        Optional<FlashSales> flashSale = flashSalesRepository.findById(id);
        flashSale.ifPresent(sale -> {
            flashSalesRepository.deleteById(id);
            productService.resetDiscountedPrice(sale.getProductId());
        });
    }
}
