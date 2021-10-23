package com.example.demo.login;

import com.example.demo.Email;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import com.example.demo.user.UserRole;
import org.springframework.context.annotation.Bean;
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

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/")
public class LoginController {

    private final UserEmployeeService userEmployeeService;
    private final Email email;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private String userName = "";

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
    public ModelAndView loginCheck(@RequestParam(name = "username", required = true) String username,
                                   @RequestParam(name = "password", required = true) String password){

        boolean locked = false;
        ModelAndView modelAndView = new ModelAndView();
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(username);
        if(userEmployee.isPresent()) {
            boolean correct = bCryptPasswordEncoder.matches(password, userEmployee.get().getPassword());
            if(correct) {
                if(verifiedPasswordTemporary(userEmployee.get())) {
                    userName = userEmployee.get().getUsername();
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken( userEmployee.get().getUsername(),
                                    userEmployee.get().getPassword(),
                                    addRole("CHANGE_PASSWORD") );

                    setAuthentication(authentication);
                    System.out.println(authentication.getAuthorities());
                    return new ModelAndView("redirect:/change-password");
                }

                if(userEmployee.get().getIsDoubleAuthenticator()) {
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userEmployee.get().getUsername(),
                                    userEmployee.get().getPassword(),
                                    addRole("AUTHENTICATOR"));

                    setAuthentication(authentication);
                    System.out.println(authentication.getAuthorities());
                    return new ModelAndView("redirect:/authentication/"+userEmployee.get().getIdUser());

                } else{
                    setAuthentication(getAuthentication(userEmployee.get()));
                    return new ModelAndView("redirect:/home");
                }
            }
            else {
                userEmployeeService.updateUserAttempts(username);
                locked = userEmployeeService.chekcedAttempts(username);
                if(locked) {
                    email.sendEmailLocker(username);
                    modelAndView.setViewName("redirect:/login?error=3");
                    return modelAndView;
                }
                modelAndView.setViewName("redirect:/login?error=2");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("redirect:/login?error=1");
            return modelAndView;
        }
    }


    //Methods
    private Authentication getAuthentication(UserEmployee userEmployee){
        return new UsernamePasswordAuthenticationToken(
                userEmployee.getUsername(),
                userEmployee.getPassword(),
                userEmployee.getAuthorities()
        );
    }

    private Collection<? extends GrantedAuthority> addRole(String nameRole) {
        switch (nameRole){
            case "CHANGE_PASSWORD":
                return Collections.singleton(new SimpleGrantedAuthority(UserRole.CHANGE_PASSWORD.name()));
            case "AUTHENTICATOR":
                return Collections.singleton(new SimpleGrantedAuthority(UserRole.AUTHENTICATOR.name()));
            default:
                return null;
        }
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean verifiedPasswordTemporary(UserEmployee userEmployee) {
        return userEmployee.getPasswordExpiredAt().isEqual(LocalDate.now())
                || userEmployee.getTemporaryPassword();
    }

    @Bean("Username")
    public String getUserName() {
        return userName;
    }

}
