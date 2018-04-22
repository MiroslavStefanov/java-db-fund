package com.softuni.json.car_dealer.repositories;


import com.softuni.json.car_dealer.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("SELECT c FROM Customer AS c WHERE c.sales IS NOT EMPTY")
    List<Customer> findAllWithSales();
}
