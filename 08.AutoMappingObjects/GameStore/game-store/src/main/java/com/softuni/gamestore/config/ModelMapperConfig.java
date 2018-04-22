package com.softuni.gamestore.config;

import com.softuni.gamestore.model.dto.binding.GameAddBindingModel;
import com.softuni.gamestore.model.dto.view.DetailGameViewModel;
import com.softuni.gamestore.model.entities.Game;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelMapperConfig {

    private final ModelMapper mapper;

    public ModelMapperConfig(ModelMapper mapper) {
        this.mapper = mapper;
        this.initialize();
    }

    private void initialize() {
        GameAddBindingMapping();
        DetailGameMapping();
    }

    private void GameAddBindingMapping() {
        this.mapper.createTypeMap(GameAddBindingModel.class, Game.class)
                .addMappings(m -> m.map(GameAddBindingModel::getThumbnail, Game::setImageThumbnail));

        this.mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    private void DetailGameMapping() {
        Converter<BigDecimal, String> priceToString = ctx -> String.format("%.2f", ctx.getSource().doubleValue());
        Converter<Date, String> dateToString = ctx -> new SimpleDateFormat("dd-MM-yyyy").format(ctx.getSource());

        this.mapper.createTypeMap(Game.class, DetailGameViewModel.class)
                .addMappings(m -> {
                    m.using(priceToString).map(Game::getPrice, DetailGameViewModel::setPrice);
                    m.using(dateToString).map(Game::getReleaseDate, DetailGameViewModel::setReleaseDate);
                });
    }
}
