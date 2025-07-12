package com.kobbo.kobbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KobboApplication {

	public static void main(String[] args) {
		SpringApplication.run(KobboApplication.class, args);
		System.out.println("Kobbo started");
	}

}
