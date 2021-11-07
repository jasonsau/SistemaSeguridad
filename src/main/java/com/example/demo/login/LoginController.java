package com.example.demo.login;

import com.example.demo.Email;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/")
public class LoginController {

    //Atributtes
    private final UserEmployeeService userEmployeeService;
    private final Email email;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //Constructor
    public LoginController(UserEmployeeService userEmployeeService,
                           Email email,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userEmployeeService = userEmployeeService;
        this.email = email;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginView(Model model,
                            @RequestParam(name = "error", required = false) String error,
                            Authentication authentication){

        if(authentication!=null) {
            return "redirect:/home";
        }

        Map<String, String> messagesError = new HashMap<>();

        if(error!=null){
            if(Integer.parseInt(error) == 1 ) {
                messagesError.put("errorUser", "Email no registrado");
            }
            if(Integer.parseInt(error) == 2) {
                messagesError.put("errorPassword", "Password Incorrecto");
            }
            if(Integer.parseInt(error) == 3) {
                messagesError.put("locked", "El usuario ha sido bloqueado");
            }
            if(Integer.parseInt(error) == 4) {
                messagesError.put("locked", "El usuario debe ser verificado");
            }
        }
        model.addAttribute("error", messagesError);
        return "login";
    }

    @PostMapping("/login-check")
    public ModelAndView loginCheck(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "password") String password){

        boolean locked;
        if(username.isEmpty() || password.isEmpty()) {
            return new ModelAndView("redirect:/login");
        }
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(username);

        if(userEmployee.isPresent()) {
            boolean correct = bCryptPasswordEncoder.matches(password, userEmployee.get().getPassword());
            if(correct) {
                userEmployeeService.restartAttempts(userEmployee.get().getIdUser());
                if(userEmployee.get().isBlocked()) {
                    return new ModelAndView("redirect:/login?error=3");
                }
                else if(!userEmployee.get().isEnabled()) {
                    return new ModelAndView("redirect:/login?error=4");
                } else if (userEmployee.get().getIsDoubleAuthenticator()) {
                    userEmployeeService.setAuthentication(userEmployeeService.getAuthentication(
                            userEmployee.get().getUsername(),
                            userEmployee.get().getPassword(),
                            userEmployeeService.addRole("AUTHENTICATOR")
                    ));
                    return new ModelAndView("redirect:/options");
                }
                else {
                    userEmployeeService. setAuthentication(userEmployeeService.getAuthentication(userEmployee.get()));
                    return new ModelAndView("redirect:/add-2fac");
                }
            }
            else {
                userEmployeeService.updateUserAttempts(username);
                locked = userEmployeeService.chekcedAttempts(username);
                if(locked) {
                    email.sendEmailLocker(userEmployee.get().getEmployee().getEmailEmployee());
                    return new ModelAndView("redirect:/login?error=3");
                }
                return new ModelAndView("redirect:/login?error=2");
            }
        } else {
            return new ModelAndView("redirect:/login?error=1");
        }
    }
}
