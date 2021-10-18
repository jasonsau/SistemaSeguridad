package com.example.demo.login;

import com.example.demo.Email;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class LoginController {

    private final UserEmployeeService userEmployeeService;
    private final FailureHandler failureHandler;
    private final Email email;

    @GetMapping("/login")
    public String loginView(Model model,
                            @RequestParam(name="error", required = false) String error){

        Map<String, String> messagesError = new HashMap<String, String>();
        boolean locked = false;

        if(error!=null){
            if(Integer.parseInt(error) == 1 ) {
                messagesError.put("email", "Email no registrado");
                throw new BadCredentialsException("Password Incorrecto");
            }

            if(Integer.parseInt(error) == 2) {
                if(userEmployeeService.updateUserAttempts(failureHandler.getParameterEmail()) == 0) {
                    throw new IllegalStateException("No se ha podido actualizar los intentos");
                }
                messagesError.put("password", "Password Incorrecto");
                locked = userEmployeeService.chekcedAttempts(failureHandler.getParameterEmail());
                if(locked) {
                    email.sendEmailLocker(failureHandler.getParameterEmail());
                    messagesError.put("locked", "El usuario ha sido bloqueado");
                }
            }
        }
        model.addAttribute("error", messagesError);
        return "login";
    }

}
