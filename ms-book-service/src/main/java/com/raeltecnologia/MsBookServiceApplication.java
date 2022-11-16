package com.raeltecnologia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsBookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBookServiceApplication.class, args);
	}

}
