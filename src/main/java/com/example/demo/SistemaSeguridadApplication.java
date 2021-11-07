package com.example.demo;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SistemaSeguridadApplication {

	private static final String ACCOUNT_SID = "ACf8ec6b5e02f25200f14b1bdabe376f55";
	private static final String AUTH_TOKEN = "ac4b4a222f38a9d0a86289c114939998";


	public static void main(String[] args) {
		SpringApplication.run(SistemaSeguridadApplication.class, args);


			/*Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("+50376288036"),
					new com.twilio.type.PhoneNumber("+15203919132"),
					"su codigo es"
			).create();
			System.out.println(message.getSid());*/


	}
}







