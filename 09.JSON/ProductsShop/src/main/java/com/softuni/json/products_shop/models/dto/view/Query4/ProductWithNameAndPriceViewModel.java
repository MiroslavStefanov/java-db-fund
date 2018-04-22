package com.softuni.json.products_shop.models.dto.view.Query4;

import java.math.BigDecimal;

public class ProductWithNameAndPriceViewModel {

    private String name;
    private BigDecimal price;

    public ProductWithNameAndPriceViewModel(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductWithNameAndPriceViewModel() {
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
}
