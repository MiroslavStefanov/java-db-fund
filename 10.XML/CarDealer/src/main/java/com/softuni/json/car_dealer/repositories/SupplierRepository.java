package com.softuni.json.car_dealer.repositories;

import com.softuni.json.car_dealer.models.dto.view.Query3.LocalSupplierViewModel;
import com.softuni.json.car_dealer.models.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT new com.softuni.json.car_dealer.models.dto.view.Query3.LocalSupplierViewModel(s.id, s.name, COUNT(p)) " +
            "FROM Supplier AS s " +
            "LEFT OUTER JOIN s.parts AS p " +
            "WHERE s.importer = false " +
            "GROUP BY s.id")
    List<LocalSupplierViewModel> findAllLocalSuppliers();
}
