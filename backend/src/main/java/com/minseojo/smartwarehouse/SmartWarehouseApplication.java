package com.minseojo.smartwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartWarehouseApplication.class, args);
	}

}
