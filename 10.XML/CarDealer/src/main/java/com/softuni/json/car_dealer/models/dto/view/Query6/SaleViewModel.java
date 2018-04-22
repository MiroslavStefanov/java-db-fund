package com.softuni.json.car_dealer.models.dto.view.Query6;

import com.softuni.json.car_dealer.models.dto.view.Query4.CarWithoutPartsViewModel;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleViewModel implements Serializable {

    private CarWithoutPartsViewModel car;
    private String customerName;
    private Double Discount;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;

    public SaleViewModel() {
    }

    public CarWithoutPartsViewModel getCar() {
        return car;
    }

    public void setCar(CarWithoutPartsViewModel car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public void calc() {
        this.priceWithDiscount = this.price.multiply(BigDecimal.valueOf(1.0 - this.Discount/100.0));
    }
}
