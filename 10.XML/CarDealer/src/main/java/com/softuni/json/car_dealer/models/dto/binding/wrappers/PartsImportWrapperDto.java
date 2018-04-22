package com.softuni.json.car_dealer.models.dto.binding.wrappers;

import com.softuni.json.car_dealer.models.dto.binding.PartImportBindingModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsImportWrapperDto {

    @XmlElement(name = "part")
    private List<PartImportBindingModel> parts;

    public PartsImportWrapperDto() {
    }

    public List<PartImportBindingModel> getParts() {
        return parts;
    }
}
