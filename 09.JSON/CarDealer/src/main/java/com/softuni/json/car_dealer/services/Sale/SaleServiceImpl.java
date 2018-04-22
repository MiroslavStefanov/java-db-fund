package com.softuni.json.car_dealer.services.Sale;

import com.softuni.json.car_dealer.models.dto.binding.SaleCreateBindingModel;
import com.softuni.json.car_dealer.models.dto.view.Query6.SaleViewModel;
import com.softuni.json.car_dealer.models.entities.Sale;
import com.softuni.json.car_dealer.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapper mapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper mapper) {
        this.saleRepository = saleRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(List<SaleCreateBindingModel> sales) {
        this.saleRepository.saveAll(
                sales
                        .stream()
                        .map(s -> this.mapper.map(s, Sale.class))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<SaleViewModel> getAll() {
        return this.saleRepository.findAll().stream().map(x -> this.mapper.map(x, SaleViewModel.class)).peek(SaleViewModel::calc).collect(Collectors.toList());
    }
}
