package com.example.demo.login;

import com.example.demo.Email;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import com.example.demo.user.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
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

    @GetMapping("/login")
    public String loginView(Model model,
                            @RequestParam(name = "error", required = false) String error){

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
                if(userEmployee.get().isBlocked() || !userEmployee.get().isEnabled()) {
                    return new ModelAndView("redirect:/login?error=3");
                }else if(userEmployeeService.verifiedPasswordTemporary(userEmployee.get())) {
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken( userEmployee.get().getUsername(),
                                    userEmployee.get().getPassword(),
                                    userEmployeeService.addRole("CHANGE_PASSWORD") );

                    userEmployeeService.setAuthentication(authentication);
                    return new ModelAndView("redirect:/change-password");
                } else if (userEmployee.get().getIsDoubleAuthenticator()) {
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userEmployee.get().getUsername(),
                                    userEmployee.get().getPassword(),
                                    userEmployeeService.addRole("AUTHENTICATOR"));
                    userEmployeeService.setAuthentication(authentication);
                    return new ModelAndView("redirect:/options");
                }
                else {
                    userEmployeeService. setAuthentication(userEmployeeService.getAuthentication(userEmployee.get()));
                    return new ModelAndView("redirect:/home");
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
