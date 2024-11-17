package com.aimnode.product_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("product")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
