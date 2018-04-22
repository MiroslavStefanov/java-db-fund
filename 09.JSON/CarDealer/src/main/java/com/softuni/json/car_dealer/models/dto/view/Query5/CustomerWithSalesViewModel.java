package com.softuni.json.car_dealer.models.dto.view.Query5;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerWithSalesViewModel implements Serializable {

    private String fullName;
    private Integer boughtCars;
    private BigDecimal spentMoney;

    public CustomerWithSalesViewModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
