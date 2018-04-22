package com.softuni.json.car_dealer.services.Sale;

import com.softuni.json.car_dealer.models.dto.binding.SaleCreateBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query6.SaleViewModel;

import java.util.List;

public interface SaleService {

    void save(List<SaleCreateBindingModel> sales);

    List<SaleViewModel> getAll();
}
