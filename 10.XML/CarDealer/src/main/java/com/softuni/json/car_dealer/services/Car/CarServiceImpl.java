package com.softuni.json.car_dealer.services.Car;

import com.softuni.json.car_dealer.models.dto.binding.CarImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query4.CarWithPartsWrapperViewModel;
import com.softuni.json.car_dealer.models.entities.Car;
import com.softuni.json.car_dealer.parser.Parser;
import com.softuni.json.car_dealer.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;
    private final Parser parser;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper, Parser parser) {
        this.carRepository = carRepository;
        this.mapper = mapper;
        this.parser = parser;
    }

    @Override
    public void save(List<CarImportBindingModel> cars) {
        this.carRepository.saveAll(
                cars
                        .stream()
                        .map(x -> this.mapper.map(x, Car.class))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public long getCount() {
        return this.carRepository.count();
    }

    @Override
    public String serializeByMake(String make) {
        return //this.gson.toJson(this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make));
        null;
    }

    @Override
    public List<CarWithPartsWrapperViewModel> getAllAlongWithParts() {
        return this.carRepository.findAll().stream().map(c -> this.mapper.map(c, CarWithPartsWrapperViewModel.class)).collect(Collectors.toList());
    }
}
