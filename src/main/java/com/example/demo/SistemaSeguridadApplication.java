package com.example.demo;

import com.example.demo.paswword.PasswordHistory;
import com.example.demo.paswword.PasswordHistoryService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableScheduling
public class SistemaSeguridadApplication {



	public static void main(String[] args) {
		SpringApplication.run(SistemaSeguridadApplication.class, args);



	}
}





