package com.nayra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NayraProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NayraProducerServiceApplication.class, args);
	}

}
