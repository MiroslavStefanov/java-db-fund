package com.softuni.json.products_shop.models.dto.binding;

public class UserCreateBindingModel implements Validable {

    private String firstName;
    private String lastName;
    private Integer age;

    public UserCreateBindingModel() {
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

    @Override
    public boolean validate() {
        return this.lastName != null && this.lastName.length() >= 3;
    }
}
