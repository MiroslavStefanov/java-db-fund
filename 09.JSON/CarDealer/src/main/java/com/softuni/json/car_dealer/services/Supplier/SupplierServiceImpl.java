package com.softuni.json.car_dealer.services.Supplier;

import com.softuni.json.car_dealer.models.dto.binding.SupplierImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query3.LocalSupplierViewModel;
import com.softuni.json.car_dealer.models.entities.Supplier;
import com.softuni.json.car_dealer.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(List<SupplierImportBindingModel> suppliers) {
        this.supplierRepository.saveAll(
            suppliers.stream().map(x -> this.mapper.map(x, Supplier.class)).collect(Collectors.toList())
        );
    }

    @Override
    public long getCount() {
        return this.supplierRepository.count();
    }

    @Override
    public List<LocalSupplierViewModel> getAllLocalSuppliers() {
        return this.supplierRepository.findAllLocalSuppliers();
    }
}
