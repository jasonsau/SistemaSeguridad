package com.example.demo.user;

import com.example.demo.responsebody.ResponseBodyUserEmail;
import com.example.demo.security.twofactor.email.ConfirmationToken;
import com.example.demo.security.twofactor.email.ConfirmationTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserEmployeeController {
    private final UserEmployeeService userEmployeeService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserEmployeeController( UserEmployeeService userEmployeeService,
                                  ConfirmationTokenService confirmationTokenService) {
        this.userEmployeeService = userEmployeeService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("home")
    public ModelAndView homeView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }

    @GetMapping("change-password")
    public ModelAndView changePassword(@RequestParam(name = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(error!=null) {
            if(error.equals("1")) {
                errors.put("errorLength", "La contraseña deber ser igual o mayor a 12 caracteres");
            }
            if(error.equals("2")) {
                errors.put("errorNoEquals", "Las contraseña no coinciden");
            }

        }
        model.addObject("errors", errors);
        model.setViewName("/password/changePassword");
        return model;
    }

    @PostMapping("change-password-check")
    public ModelAndView changePasswordCheck(Authentication authentication,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "passwordVerified") String passwordVerified) {
        if(password.length()<12) {
            return new ModelAndView("redirect:/change-password?error=1");
        }
        if(!password.equals(passwordVerified)) {
            return new ModelAndView("redirect:/change-password?error=2");
        }
        int response = userEmployeeService.updatePassword(password, authentication.getName());
        if(response == 1) {
            SecurityContextHolder.clearContext();
            return new ModelAndView("redirect:/login");
        } else {
            throw new IllegalStateException("No se ha podido actualizar el password");
        }
    }
    
    @GetMapping("registration")
    public ModelAndView viewRegister() {
    return new ModelAndView("register");
    }
    
    @PostMapping("register-check")
    public ModelAndView viewCheckRegister()
    {
    	ModelAndView model= new ModelAndView();
    	return model ;
    }

    @GetMapping("/users")
    public List<UserEmployee> selectUsers() {
        return userEmployeeService.selectUsers();
    }

    @PostMapping("/unlocked-user")
    public Map<String, String> unlockedUser(@RequestBody ResponseBodyUserEmail responseBodyUserEmail) {
        Map<String, String> messages = new HashMap<>();
        Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(responseBodyUserEmail.getEmail());

        if(userEmployee.isPresent()) {
            if(userEmployee.get().isBlocked()) {
                ConfirmationToken confirmationToken = confirmationTokenService
                        .createNewToken(userEmployee.get());
                confirmationTokenService.insertConfirmationToken(confirmationToken);
                messages.put("message", "Se le ha enviado a su correo las indicaciones");
                messages.put("number", "1");
            } else {
                messages.put("message", "Su usuario no esta bloqueado");
                messages.put("number", "2");
            }
        } else {
            messages.put("message", "No se ha encontrado el usuario");
            messages.put("number", "3");
        }
        return  messages;
    }
    
}
