package com.example.demo;

import com.example.demo.security.twofactor.TwoFactorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaSeguridadApplication {
	private static TwoFactorService twoFactorService;

	public SistemaSeguridadApplication(TwoFactorService twoFactorService) {
		this.twoFactorService = twoFactorService;
		System.out.println(twoFactorService.generateSecretKey());
	}

	public static void main(String[] args) {
		SpringApplication.run(SistemaSeguridadApplication.class, args);

	}

}





