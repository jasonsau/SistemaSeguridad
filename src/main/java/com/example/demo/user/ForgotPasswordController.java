package com.example.demo.user;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserEmployeeService userEmployeeService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("pageTitle","Recuperar Contraseña");
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model){
        String email = request.getParameter("employee_email");
        String token = RandomString.make(45);

        try {
            userEmployeeService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;

            sendEmail(email, resetPasswordLink);

            model.addAttribute("message","Se ha enviado un enlace de reestableciemto de contraseña a su correo electronico. Revise por favor");

        } catch (UserEmployeeNotFoundException ex) {
            model.addAttribute("error",ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error","Error al enviar el email");
        }

        model.addAttribute("pageTitle","Recuperar Contraseña");
        return "forgot_password_form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@gmail.com","Tech Support");
        helper.setTo(email);

        String subject = "Enlace para reestablecer contraseña";
        String content = "<p>Hola,</p>"
                + "<p>Ha solicitado restablecer su contraseña.</p>"
                + "<p>Haga clic en el siguiente enlace para cambiar su contraseña: </p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Cambiar mi contraseña</a></p>"
                + "<br>"
                + "<p>Ignore este correo electrónico si recuerda su contraseña, "
                + "o no ha realizado la solicitud.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UserEmployee userEmployee = userEmployeeService.get(token);
        /*model.addAttribute("token", token);*/

        if (userEmployee == null) {
            model.addAttribute("title", "Reestablecer Contraseña");
            model.addAttribute("message", "Token Invalido");
            return "message";
        }

        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reestablecer Contraseña");

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirnPassword = request.getParameter("confirnPassword");

        UserEmployee userEmployee = userEmployeeService.get(token);

        if (userEmployee == null) {
            model.addAttribute("title", "Reestablecer Contraseña");
            model.addAttribute("message", "Enlace incorrecto");
        }
        if(password != confirnPassword) {
            model.addAttribute("message","Contraseñas no coinciden");
            model.addAttribute("pageTitle","Reestablecer Contraseña");
            return "reset_password_form";
        }
        else {
            userEmployeeService.updateNewPassword(userEmployee, password);
            model.addAttribute("title", "Reestablecer Contraseña");
            model.addAttribute("message", "Cambio de contraseña exitoso");
        }

        return "message";
    }
}
