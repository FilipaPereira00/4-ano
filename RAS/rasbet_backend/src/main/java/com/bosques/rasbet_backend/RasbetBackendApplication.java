package com.bosques.rasbet_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class RasbetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RasbetBackendApplication.class, args);
	}

}
