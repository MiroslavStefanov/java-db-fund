package com.softuni.json.car_dealer.services.Car;

import com.softuni.json.car_dealer.models.dto.binding.CarImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query4.CarWithPartsWrapperViewModel;

import java.util.List;

public interface CarService {

    void save(List<CarImportBindingModel> cars);

    long getCount();

    String serializeByMake(String make);

    List<CarWithPartsWrapperViewModel> getAllAlongWithParts();
}
