package com.softuni.json.car_dealer.services.Part;

import com.softuni.json.car_dealer.models.dto.binding.PartImportBindingModel;
import com.softuni.json.car_dealer.models.entities.Part;
import com.softuni.json.car_dealer.repositories.PartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper mapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper mapper) {
        this.partRepository = partRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(List<PartImportBindingModel> parts) {
        this.partRepository.saveAll(
                parts
                .stream()
                .map(x -> this.mapper.map(x, Part.class))
                .collect(Collectors.toList())
        );
    }

    @Override
    public long getCount() {
        return this.partRepository.count();
    }
}
