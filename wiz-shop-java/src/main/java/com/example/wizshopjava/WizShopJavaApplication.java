package com.example.wizshopjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WizShopJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WizShopJavaApplication.class, args);
	}

}
