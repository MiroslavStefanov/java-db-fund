package com.softuni.json.products_shop.models.dto.view.Query4;


public class UserWithAgeAndSoldProductViewModel {

    private String firstName;
    private String lastName;
    private Integer age;
    private SoldProductsInfoViewModel soldProducts;

    public UserWithAgeAndSoldProductViewModel(String firstName, String lastName, Integer age, SoldProductsInfoViewModel soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

    public UserWithAgeAndSoldProductViewModel() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SoldProductsInfoViewModel getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsInfoViewModel soldProducts) {
        this.soldProducts = soldProducts;
    }
}
