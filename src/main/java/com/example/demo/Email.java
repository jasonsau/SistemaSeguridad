package com.example.demo;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;



@Service
public class Email {

    private final JavaMailSender javaMailSender;
    private static final String ACCOUNT_SID = "ACf8ec6b5e02f25200f14b1bdabe376f55";
    private static final String AUTH_TOKEN = "bbf5fa72dd29e0dc702c2319ece6a54f";

    public Email(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public static final String ACCOUNTLOCKED = "<h1>Recursos Humanos</h1>\n <h2>Cuenta Bloqueada</h2> \n " +
            "<p>Lamentamos" +
            " " +
            "comunicarle que su cuenta ha sido bloqueada por terminos de seguridad</p";


    @Async
    public void sendEmailLocker(String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(ACCOUNTLOCKED, true);
            helper.setTo(email);
            helper.setSubject("Cuenta Bloqueada");
            helper.setFrom("admin@recursoshumanos.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error al enviar el email");
        }
    }

    @Async
    public void sendEmailToken(String email, String html, String subject){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(html, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom("admin@recursoshumanos.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error al enviar el email");
        }

    }
}
