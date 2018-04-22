package com.softuni.gamestore;

import com.softuni.gamestore.config.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameStoreApplication.class, args);
	}

	@Bean
	public ModelMapper getMapper() {
	    ModelMapper mapper = new ModelMapper();
        ModelMapperConfig config = new ModelMapperConfig(mapper);
        return mapper;
    }
}
