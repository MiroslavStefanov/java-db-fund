package com.softuni.json.car_dealer.models.dto.view.Query4;

import java.io.Serializable;

public class CarWithoutPartsViewModel implements Serializable {

    private String Make;
    private String Model;
    private Long TravelledDistance;

    public CarWithoutPartsViewModel() {
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        TravelledDistance = travelledDistance;
    }

    public void setTravelledDistance(Object v) {
        if(v.getClass() != Long.class)
            return;
        this.setTravelledDistance((Long)v);
    }
}
