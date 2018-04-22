package com.softuni.json.car_dealer.services.Customer;

import com.softuni.json.car_dealer.models.dto.binding.CustomerImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query5.CustomerWithSalesViewModel;

import java.util.List;

public interface CustomerService {

    void save(List<CustomerImportBindingModel> customers);

    long getCount();

    String serialize();

    List<CustomerWithSalesViewModel> getAllWithSales();
}
