package com.example.demo;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SistemaSeguridadApplication {

	private static final String ACCOUNT_SID = "ACd532c779181ca12b3cf9b8e6bf96d0c5";
	private static final String AUTH_TOKEN = "f5d3cb0630cac709f87376fa33e7d65e";


	public static void main(String[] args) {
		SpringApplication.run(SistemaSeguridadApplication.class, args);


			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("+50360027627"),
					new com.twilio.type.PhoneNumber("+13185133334"),
					"su codigo es"
			).create();
			System.out.println(message.getSid());


	}
}







