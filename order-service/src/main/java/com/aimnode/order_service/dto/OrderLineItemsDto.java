package com.aimnode.order_service.dto;

import java.math.BigDecimal;

public class OrderLineItemsDto {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    // Getters and setters
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
