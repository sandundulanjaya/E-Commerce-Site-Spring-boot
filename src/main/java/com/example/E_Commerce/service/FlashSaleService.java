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

    public FlashSales createFlashSale(FlashSales flashSale) {
        return flashSalesRepository.save(flashSale);
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
        flashSalesRepository.deleteById(id);
    }
}
