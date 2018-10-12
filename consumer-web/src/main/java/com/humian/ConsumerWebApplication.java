package com.humian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConsumerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerWebApplication.class, args);
	}
}
