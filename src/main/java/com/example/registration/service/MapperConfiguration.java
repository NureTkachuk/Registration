package com.example.registration.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    private static final ModelMapper mapper = new ModelMapper();


    public static ModelMapper modelMapper() {
        return mapper;
    }
}
