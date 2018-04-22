package com.softuni.json.car_dealer.models.entities;

import com.softuni.json.car_dealer.annotations.SkipSerialization;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale {

    private Integer id;
    private Car car;
    @SkipSerialization
    private Customer customer;
    private Double discount;

    public Sale() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
