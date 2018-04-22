package com.softuni.json.car_dealer.models.dto.binding.wrappers;

import com.softuni.json.car_dealer.models.dto.binding.SupplierImportBindingModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersImportWrapperDto {

    @XmlElement(name = "supplier")
    private List<SupplierImportBindingModel> suppliers;

    public SuppliersImportWrapperDto() {
    }

    public List<SupplierImportBindingModel> getSuppliers() {
        return suppliers;
    }
}
