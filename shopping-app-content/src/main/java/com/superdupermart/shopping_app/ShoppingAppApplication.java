package com.superdupermart.shopping_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ShoppingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApplication.class, args);
	}

}
