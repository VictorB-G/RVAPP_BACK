package com.victorb.reservapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
    	return new ModelMapper();
	}

}
