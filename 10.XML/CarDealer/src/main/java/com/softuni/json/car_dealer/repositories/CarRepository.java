package com.softuni.json.car_dealer.repositories;

import com.softuni.json.car_dealer.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
