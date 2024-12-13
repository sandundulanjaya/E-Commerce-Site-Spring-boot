package com.example.E_Commerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Document("flashsales")
@AllArgsConstructor
@NoArgsConstructor
public class FlashSales {
    @Id
    private String id;
    private String productId;
    private BigDecimal discountedPrice;
    private Date startDate;
    private Date endDate;
}
