package com.softuni.json.car_dealer.models.dto.binding;

public class SaleCreateBindingModel {

    private Integer car;
    private Integer customer;
    private Double discount;

    public SaleCreateBindingModel(Integer car, Integer customer, Double discount) {
        this.car = car;
        this.customer = customer;
        this.discount = discount;
    }

    public SaleCreateBindingModel() {
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
