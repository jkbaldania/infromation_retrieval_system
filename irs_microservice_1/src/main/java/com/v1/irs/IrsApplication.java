package com.v1.irs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrsApplication.class, args);
	}

}
