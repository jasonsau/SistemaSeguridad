package com.example.demo.login;

import com.example.demo.Email;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class LoginController {

    private final UserEmployeeService userEmployeeService;
    private final Email email;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginView(Model model,
                            @RequestParam(name = "error", required = false) String error){

        Map<String, String> messagesError = new HashMap<String, String>();

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
                                   @RequestParam(name = "password", required = true) String password,
                                   HttpSession httpSession){

        boolean locked = false;
        ModelAndView modelAndView = new ModelAndView();
        Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(username);
        if(userEmployee.isPresent()) {
            boolean correct = bCryptPasswordEncoder.matches(password, userEmployee.get().getPasswordUserEmployee());
            if(correct) {
                if(userEmployee.get().getIsDoubleAuthenticator()) {
                    return new ModelAndView("redirect:/authentication/"+userEmployee.get().getIdUser());

                } else{
                    SecurityContextHolder.getContext().setAuthentication(getAuthentication(userEmployee.get()));
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

    private Authentication getAuthentication(UserEmployee userEmployee){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userEmployee.getUserNameEmployee(),
                userEmployee.getPasswordUserEmployee(),
                userEmployee.getAuthorities()
        );
        return authentication;
    }

}
