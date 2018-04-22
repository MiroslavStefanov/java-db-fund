package com.softuni.json.car_dealer.config;


import com.softuni.json.car_dealer.models.dto.binding.CarImportBindingModel;
import com.softuni.json.car_dealer.models.dto.binding.PartImportBindingModel;
import com.softuni.json.car_dealer.models.dto.binding.SaleCreateBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query4.CarWithPartsWrapperViewModel;
import com.softuni.json.car_dealer.models.dto.view.Query4.CarWithoutPartsViewModel;
import com.softuni.json.car_dealer.models.dto.view.Query4.PartNameAndPriceViewModel;
import com.softuni.json.car_dealer.models.dto.view.Query5.CustomerWithSalesViewModel;
import com.softuni.json.car_dealer.models.dto.view.Query6.SaleViewModel;
import com.softuni.json.car_dealer.models.entities.*;
import com.softuni.json.car_dealer.repositories.CarRepository;
import com.softuni.json.car_dealer.repositories.CustomerRepository;
import com.softuni.json.car_dealer.repositories.PartRepository;
import com.softuni.json.car_dealer.repositories.SupplierRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@Transactional
public class MapperConfig {

    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    private final ModelMapper mapper;

    @Autowired
    public MapperConfig(SupplierRepository supplierRepository,
                        PartRepository partRepository,
                        CarRepository carRepository,
                        CustomerRepository customerRepository) {
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        importPartsMapping();
        importCarsMapping();
        createSaleMapping();
        exportCarsWithPartsMapping();
        exportCustomersWithSales();
        exportSales();
    }

    private void importPartsMapping() {
        Converter<Integer, Supplier> con = ctx -> this.supplierRepository.findById(ctx.getSource()).orElse(null);

        this.mapper.createTypeMap(PartImportBindingModel.class, Part.class).addMappings(
                m -> m.using(con).map(PartImportBindingModel::getSupplier, Part::setSupplier)
        );
    }

    private void importCarsMapping() {
        Converter<Set<Integer>, Set<Part>> con = ctx -> {
            List<Part> parts = this.partRepository.findAllById(ctx.getSource());
            Set<Part> ret = new HashSet<>();
            ret.addAll(parts);
            return ret;
        };

        this.mapper.createTypeMap(CarImportBindingModel.class, Car.class).addMappings(
                m -> m.using(con).map(CarImportBindingModel::getParts, Car::setParts)
        );
    }

    private void createSaleMapping() {
        Converter<Integer, Car> carCon = ctx -> this.carRepository.findById(ctx.getSource()).orElse(null);
        Converter<Integer, Customer> custCon = ctx -> this.customerRepository.findById(ctx.getSource()).orElse(null);

        this.mapper.createTypeMap(SaleCreateBindingModel.class, Sale.class).addMappings(m -> {
            m.using(carCon).map(SaleCreateBindingModel::getCar, Sale::setCar);
            m.using(custCon).map(SaleCreateBindingModel::getCustomer, Sale::setCustomer);
        });
    }

    private void exportCarsWithPartsMapping() {
        Converter<Set<Part>, Set<PartNameAndPriceViewModel>> partCon = ctx -> {
            Set<PartNameAndPriceViewModel> ret = new HashSet<>();
            ctx.getSource().forEach(p -> {
                ret.add(this.mapper.map(p, PartNameAndPriceViewModel.class));
            });

            return ret;
        };

        this.mapper.createTypeMap(Car.class, CarWithPartsWrapperViewModel.class).addMappings(m -> {
            m.using(partCon).map(Car::getParts, CarWithPartsWrapperViewModel::setParts);
            m.map(Car::getMake, (dst, v) -> dst.getCar().setMake(String.valueOf(v)));
            m.map(Car::getModel, (dst, v) -> dst.getCar().setModel(String.valueOf(v)));
            m.map(Car::getTravelledDistance, (dst, v) -> dst.getCar().setTravelledDistance(v));
        });
    }

    private void exportCustomersWithSales() {

        Converter<Set<Sale>, Integer> countCon = ctx -> ctx.getSource().size();
        Converter<Set<Sale>, BigDecimal> spentCon = ctx -> {
            Optional<BigDecimal> b =
            ctx.getSource()
                    .stream()
                    .map(x -> {
                        double disc = x.getDiscount();
                        if(x.getCustomer().getYoungDriver())
                            disc += 5.0;
                        Optional<BigDecimal> total = x.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add);
                        if(total.isPresent())
                            return total.get().multiply(BigDecimal.valueOf(1 - disc / 100.0));
                        else
                            return BigDecimal.ZERO;
            })
                    .reduce(BigDecimal::add);
            BigDecimal ans = b.orElse(BigDecimal.ZERO);
            return ans.setScale(2, BigDecimal.ROUND_HALF_UP);
        };

        this.mapper.createTypeMap(Customer.class, CustomerWithSalesViewModel.class).addMappings(m -> {
            m.map(Customer::getName, CustomerWithSalesViewModel::setFullName);
            m.using(countCon).map(Customer::getSales, CustomerWithSalesViewModel::setBoughtCars);
            m.using(spentCon).map(Customer::getSales, CustomerWithSalesViewModel::setSpentMoney);
        });
    }

    private void exportSales() {

        Converter<Car, BigDecimal> priceCon = ctx -> ctx.getSource().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        Converter<Car, CarWithoutPartsViewModel> carCon = ctx -> {
            CarWithoutPartsViewModel model = new CarWithoutPartsViewModel();
            Car car = ctx.getSource();
            model.setMake(car.getMake());
            model.setModel(car.getModel());
            model.setTravelledDistance(car.getTravelledDistance());
            return model;
        };

        this.mapper.createTypeMap(Sale.class, SaleViewModel.class).addMappings(m -> {
            m.using(carCon).map(Sale::getCar, SaleViewModel::setCar);
            m.map(src -> src.getCustomer().getName(), SaleViewModel::setCustomerName);
            m.using(priceCon).map(Sale::getCar, SaleViewModel::setPrice);
        });
    }

    @Bean
    public ModelMapper getMapper() {
        return mapper;
    }
}
