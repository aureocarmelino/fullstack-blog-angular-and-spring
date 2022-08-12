package com.aureocarmelino.imperblogbackend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig 
{
	@Bean
	protected ModelMapper createModelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}