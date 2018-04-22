package com.softuni.json.products_shop.models.dto.view.Query2;

import java.util.HashSet;
import java.util.Set;

public class UserWithSoldProductsViewModel {

    private String firstName;
    private String lastName;
    private Set<SoldProductViewModel> soldProducts;

    public UserWithSoldProductsViewModel() {
        soldProducts = new HashSet<>();
    }

    public UserWithSoldProductsViewModel(String firstName, String lastName, Set<SoldProductViewModel> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<SoldProductViewModel> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<SoldProductViewModel> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
