package com.softuni.json.car_dealer.services.Part;

import com.softuni.json.car_dealer.models.dto.binding.PartImportBindingModel;

import java.util.List;

public interface PartService {

    void save(List<PartImportBindingModel> parts);

    long getCount();
}
