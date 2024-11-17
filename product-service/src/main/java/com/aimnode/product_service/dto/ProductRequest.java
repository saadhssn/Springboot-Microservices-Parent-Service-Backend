package com.aimnode.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor // Add public to generated constructor
public class ProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
}
