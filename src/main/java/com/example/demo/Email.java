package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class Email {

    private final JavaMailSender mailSender;

    public static final String ACCOUNTLOCKED = "<h1>Recursos Humanos</h1>\n <h2>Cuenta Bloqueada</h2> \n " +
            "<p>Lamentamos" +
            " " +
            "comunicarle que su cuenta ha sido bloqueada por terminos de seguridad</p";


    @Async
    public String sendEmailLocker(String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(ACCOUNTLOCKED, true);
            helper.setTo(email);
            helper.setSubject("Cuenta Bloqueada");
            helper.setFrom("admin@recursoshumanos.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error al enviar el email");
        }
        return "Enviado";
    }
}
