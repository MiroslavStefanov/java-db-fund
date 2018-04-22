package com.softuni.json.car_dealer.models.dto.binding.wrappers;

import com.softuni.json.car_dealer.models.dto.binding.CustomerImportBindingModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersImportWrapperDto {

    @XmlElement(name = "customer")
    private List<CustomerImportBindingModel> customers;

    public List<CustomerImportBindingModel> getCustomers() {
        return customers;
    }
}
