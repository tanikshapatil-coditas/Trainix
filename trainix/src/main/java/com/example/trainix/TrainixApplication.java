package com.example.trainix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableJpaRepositories(basePackages = "com.example.trainix.repository")
@SpringBootApplication
public class TrainixApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainixApplication.class, args);
	}

}
