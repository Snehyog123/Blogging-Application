package com.Infopeer.Blogging_Application.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperClass {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
	}

}
