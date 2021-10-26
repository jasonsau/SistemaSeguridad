package com.example.demo.api.email.rest;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String from, String to, String subject, String body) {
		
		SimpleMailMessage emailMessage= new SimpleMailMessage();
		emailMessage.setFrom(from);
		emailMessage.setTo(to);
		emailMessage.setSubject(subject);
		emailMessage.setText(body);
		
		javaMailSender.send(emailMessage);
	}
	
}
