package com.sparta.travelshooting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TravelShootingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelShootingApplication.class, args);
	}

}
