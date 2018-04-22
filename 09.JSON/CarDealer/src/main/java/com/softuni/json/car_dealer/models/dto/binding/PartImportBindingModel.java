package com.softuni.json.car_dealer.models.dto.binding;

import java.math.BigDecimal;

public class PartImportBindingModel {

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Integer supplier;

    public PartImportBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }
}
