package com.softuni.json.car_dealer.models.dto.view.Query4;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartNameAndPriceViewModel implements Serializable {

    private String name;
    private BigDecimal price;

    public PartNameAndPriceViewModel() {
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
