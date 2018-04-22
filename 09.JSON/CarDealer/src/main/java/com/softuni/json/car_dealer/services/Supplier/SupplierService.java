package com.softuni.json.car_dealer.services.Supplier;

import com.softuni.json.car_dealer.models.dto.binding.SupplierImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query3.LocalSupplierViewModel;

import java.util.List;

public interface SupplierService {

    void save(List<SupplierImportBindingModel> suppliers);

    long getCount();

    List<LocalSupplierViewModel> getAllLocalSuppliers();
}
