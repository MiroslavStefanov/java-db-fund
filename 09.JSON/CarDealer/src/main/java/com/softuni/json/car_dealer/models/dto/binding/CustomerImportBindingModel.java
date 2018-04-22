package com.softuni.json.car_dealer.models.dto.binding;

import java.util.Date;

public class CustomerImportBindingModel {

    private String name;
    private Date birthDate;
    private Boolean isYoungDriver;

    public CustomerImportBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
