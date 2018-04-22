package com.softuni.json.car_dealer.models.dto.view.Query4;

import java.io.Serializable;
import java.util.Set;

public class CarWithPartsWrapperViewModel implements Serializable {

    private CarWithoutPartsViewModel car;
    private Set<PartNameAndPriceViewModel> parts;

    public CarWithPartsWrapperViewModel() {
    }

    public CarWithoutPartsViewModel getCar() {
        return car;
    }

    public void setCar(CarWithoutPartsViewModel car) {
        this.car = car;
    }

    public Set<PartNameAndPriceViewModel> getParts() {
        return parts;
    }

    public void setParts(Set<PartNameAndPriceViewModel> parts) {
        this.parts = parts;
    }
}
