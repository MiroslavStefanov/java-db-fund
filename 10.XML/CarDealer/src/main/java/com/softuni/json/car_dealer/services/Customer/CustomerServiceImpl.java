package com.softuni.json.car_dealer.services.Customer;

import com.softuni.json.car_dealer.models.dto.binding.CustomerImportBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query5.CustomerWithSalesViewModel;
import com.softuni.json.car_dealer.models.entities.Customer;
import com.softuni.json.car_dealer.parser.Parser;
import com.softuni.json.car_dealer.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final Parser parser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper, Parser parser) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.parser = parser;
    }

    @Override
    public void save(List<CustomerImportBindingModel> customers) {
        this.customerRepository.saveAll(
                customers
                        .stream()
                        .map(c -> this.mapper.map(c, Customer.class))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public long getCount() {
        return this.customerRepository.count();
    }

    @Override
    public String serialize() {
        return //this.gson.toJson(this.customerRepository.findAllByOrderByBirthDateAscYoungDriverAsc());
        null;
    }

    @Override
    public List<CustomerWithSalesViewModel> getAllWithSales() {
        return this.customerRepository.findAllWithSales()
                .stream()
                .map(x -> this.mapper.map(x, CustomerWithSalesViewModel.class))
                .sorted((x,y) -> {
                    int res = y.getSpentMoney().compareTo(x.getSpentMoney());
                    if(res == 0)
                        return y.getBoughtCars().compareTo(x.getBoughtCars());
                    else return res;
                })
                .collect(Collectors.toList());
    }
}
