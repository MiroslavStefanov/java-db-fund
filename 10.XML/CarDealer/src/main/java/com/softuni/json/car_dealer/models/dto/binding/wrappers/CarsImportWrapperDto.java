package com.softuni.json.car_dealer.models.dto.binding.wrappers;

import com.softuni.json.car_dealer.models.dto.binding.CarImportBindingModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsImportWrapperDto {

    @XmlElement(name = "car")
    private List<CarImportBindingModel> cars;

    public CarsImportWrapperDto() {
    }

    public List<CarImportBindingModel> getCars() {
        return cars;
    }
}
