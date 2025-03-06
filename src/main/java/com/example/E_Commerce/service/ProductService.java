package com.example.E_Commerce.service;

import com.algolia.api.SearchClient;
import com.algolia.model.search.DeleteByParams;
import com.example.E_Commerce.entity.FlashSales;
import com.example.E_Commerce.entity.Order;
import com.example.E_Commerce.entity.Product;
import com.example.E_Commerce.repository.FlashSalesRepository;
import com.example.E_Commerce.repository.OrderRepository;
import com.example.E_Commerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.Or;
import org.springframework.stereotype.Service;

import java.lang.StackWalker.Option;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FlashSalesRepository flashSalesRepository;

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);

        // Initialize the client
        SearchClient client = new SearchClient("S9NOOSY4TW", "46c163e74cf52b7779f2c24c0adf5c26");

        // Call the API
        client.deleteBy("e-commerce.products", new DeleteByParams().setFilters("_id:{$oid: "+productId+"}"));
        // >LOG

    }

    public void updateProduct(String productId, Product product) {
        productRepository.findById(productId)
                .ifPresentOrElse(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDiscountedPrice(product.getDiscountedPrice());
                    existingProduct.setQuantityInStock(product.getQuantityInStock());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setImageUrl1(product.getImageUrl1());
                    existingProduct.setImageUrl2(product.getImageUrl2());
                    existingProduct.setImageUrl3(product.getImageUrl3());
                    existingProduct.setImageUrl4(product.getImageUrl4());
                    existingProduct.setImageUrl5(product.getImageUrl5());
                    existingProduct.setRating(product.getRating());
                    productRepository.save(existingProduct);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public void addProduct(String name, String description, BigDecimal price, BigDecimal discountedPrice,
            Integer quantityInStock, String imageUrl1,
            String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, Float rating, String category) {
        try {
            Product product = new Product(null, name, description, price, discountedPrice, quantityInStock, category,
                    imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5, rating);
            productRepository.save(product);

            // Initialize the client
            SearchClient client = new SearchClient("S9NOOSY4TW", "46c163e74cf52b7779f2c24c0adf5c26");

            // Call the API
            client.saveObject(
                    "e-commerce.products",
                    new HashMap() {
                        {
                            put("name", name);
                            put("description", description);
                            put("category", category);
                            put("_id", new HashMap<String, String>() {
                                {
                                    put("$oid", product.getId());
                                }
                            });
                            put("price", price);
                            put("quantityInStock", quantityInStock);
                            put("imageUrl1", imageUrl1);
                            put("imageUrl2", imageUrl2);
                            put("imageUrl3", imageUrl3);
                            put("imageUrl4", imageUrl4);
                            put("rating", rating);

                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while adding product!");
        }

    }

    public void updateDiscountedPrice(String productId, BigDecimal discountedPrice) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    product.setDiscountedPrice(discountedPrice);
                    productRepository.save(product);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public void resetDiscountedPrice(String productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    product.setDiscountedPrice(null);
                    productRepository.save(product);
                }, () -> {
                    throw new RuntimeException("Product not found");
                });
    }

    public Product getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<FlashSales> flashSales = flashSalesRepository.findByProductId(productId);
        Date now = new Date();

        // Reset to original price first
        product.setDiscountedPrice(product.getPrice());

        // Apply active flash sale discount if exists
        flashSales.stream()
                .filter(sale -> sale.getStartDate().before(now) && sale.getEndDate().after(now))
                .findFirst()
                .ifPresent(sale -> product.setDiscountedPrice(sale.getDiscountedPrice()));

        // Return the product with the correct price
        //product.setPrice(product.getDiscountedPrice() == null ? product.getPrice() : product.getDiscountedPrice());
        return product;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        //products.forEach(product -> product.setPrice(product.getDiscountedPrice() == null ? product.getPrice() : product.getDiscountedPrice()));
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //products.forEach(product -> product.setPrice(product.getDiscountedPrice() == null ? product.getPrice() : product.getDiscountedPrice()));
        return products;
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        //products.forEach(product -> product.setPrice(product.getDiscountedPrice() == null ? product.getPrice() : product.getDiscountedPrice()));
        return products;
    }

    public Integer getProductCount() {
        return (int) productRepository.count();
    }

}
