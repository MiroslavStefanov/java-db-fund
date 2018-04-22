package com.softuni.gamestore.model.dto.view;

import java.math.BigDecimal;

public class AllGameViewModel {

    private String title;
    private BigDecimal price;

    public AllGameViewModel() {
    }

    public AllGameViewModel(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", this.title, this.price.doubleValue());
    }
}
