package com.softuni.json.car_dealer.models.dto.binding;

import java.util.HashSet;
import java.util.Set;

public class CarImportBindingModel {

    private String make;
    private String model;
    private Long travelledDistance;
    private Set<Integer> parts;

    public CarImportBindingModel() {
        this.parts = new HashSet<>();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<Integer> getParts() {
        return parts;
    }

    public void setParts(Set<Integer> parts) {
        this.parts = parts;
    }
}
