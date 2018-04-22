package com.softuni.json.products_shop.models.dto.binding;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductCreateBindingModel implements Validable {

    private String name;
    private BigDecimal price;
    private Integer buyer;
    private Integer seller;
    private Set<Integer> categories;

    public ProductCreateBindingModel() {
        this.categories = new HashSet<>();
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

    public Integer getBuyer() {
        return buyer;
    }

    public void setBuyer(Integer buyer) {
        this.buyer = buyer;
    }

    public Integer getSeller() {
        return seller;
    }

    public void setSeller(Integer seller) {
        this.seller = seller;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    @Override
    public boolean validate() {
        return this.name != null && this.name.length() >= 3;
    }
}
