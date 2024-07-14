package com.test;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeMangamentApplication {
	@Bean
public ModelMapper modelmap() {
	return new ModelMapper();
}
	public static void main(String[] args) {
		SpringApplication.run(EmployeeMangamentApplication.class, args);
	}

}
