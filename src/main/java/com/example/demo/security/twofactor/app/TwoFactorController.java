package com.example.demo.security.twofactor.app;

import com.example.demo.Email;
import com.example.demo.security.twofactor.email.ConfirmationToken;
import com.example.demo.security.twofactor.email.ConfirmationTokenService;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TwoFactorController {
    private final String ISSUER_COMPANY = "Sistema seguridad Recursos Humanos";
    private final TwoFactorService twoFactorService;
    private final UserEmployeeService userEmployeeService;
    private final ConfirmationTokenService confirmationTokenService;
    private final Email email;

    public TwoFactorController(TwoFactorService twoFactorService,
                               UserEmployeeService userEmployeeService,
                               ConfirmationTokenService confirmationTokenService,
                               Email email) {
        this.twoFactorService = twoFactorService;
        this.userEmployeeService = userEmployeeService;
        this.confirmationTokenService = confirmationTokenService;
        this.email = email;
    }

    @GetMapping(value = "barCode", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage barCode(Authentication authentication) {

        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        if(userEmployee.isPresent()) {
            return twoFactorService.createQRCode(
                    twoFactorService.getGoogleAuthenticationBarCode(
                            userEmployee.get().getSecretKeyGoogleAuthenticator(),
                            userEmployee.get().getUsername(),
                            ISSUER_COMPANY),
                    "png",
                    200,
                    200);
        }
        return null;
    }

    @GetMapping(value = "options")
    public ModelAndView optionsAuthentication() {
        return new ModelAndView("authentication/authenticationOption");
    }

    @GetMapping(value = "authentication/{idUser}" )
    public ModelAndView verificationCodeView(@RequestParam(name = "error", required = false) String error,
                                             @PathVariable(name = "idUser", required = false) Long idUser,
                                             @RequestParam(name = "tipo") String tipo,
                                             Authentication authentication) {
        Optional<UserEmployee> userEmployee = userEmployeeService.findByIdUser(idUser);
        ModelAndView model = new ModelAndView();
        ConfirmationToken confirmationToken = new ConfirmationToken();

        if (userEmployee.isPresent()) {
            if (userEmployee.get().getUsername().equals(authentication.getName())) {
                model.addObject("idUser", idUser);
                if (error == null) {
                    if (tipo.equals("app")) {
                        model.addObject("tipo", "app");
                    } else if (tipo.equals("correo")) {
                        confirmationToken = confirmationTokenService.createNewToken(userEmployee.get());
                        confirmationTokenService.insertConfirmationToken(confirmationToken);
                        model.addObject("tipo", "correo");
                        email.sendEmailToken(
                                userEmployee.get().getEmployee().getEmailEmployee(),
                                createBodyEmailConfirmationToken(confirmationToken.getToken()),
                                "Codigo de Verificacion"
                        );
                    } else {
                        model.addObject("tipo", "sms");
                    }
                } else if (error.equals("1")) {
                    if(tipo.equals("app")) {
                        model.addObject("tipo", "app");
                    }
                    if(tipo.equals("correo")) {
                        model.addObject("tipo", "correo");
                    }
                    if(tipo.equals("sms")) {
                        model.addObject("tipo", "sms");
                    }
                    model.addObject("error", "El codigo ingresado es incorrecto");

                } else if (error.equals("2")) {
                    if(tipo.equals("app")) {
                        model.addObject("tipo", "app");
                    }
                    if(tipo.equals("correo")) {
                        model.addObject("codigo", confirmationToken.getIdToken());
                        model.addObject("tipo", "correo");
                    }
                    if(tipo.equals("sms")) {
                        model.addObject("tipo", "sms");
                    }
                    model.addObject("error", "El codigo que ha ingresado ya ha expirado");
                } else {
                    SecurityContextHolder.clearContext();
                    return new ModelAndView("redirect:/login");
                }
            }
            model.setViewName("/password/AppAuthenticator");
            return model;
        }
        return null;
    }

    @PostMapping("verification-code")
    public ModelAndView verificationCode(@RequestParam(name = "codigo") String codigo,
                                         @RequestParam(name = "idUser") Long idUser,
                                         @RequestParam(name = "tipo") String tipo,
                                         Authentication authentication) {

        Optional<UserEmployee> userEmployee = userEmployeeService.findByIdUser(idUser);
        if(userEmployee.isPresent()) {
            if(userEmployee.get().isBlocked()) {
                authentication = userEmployeeService.getAuthentication(authentication.getName(),
                        authentication.getCredentials().toString(),
                        userEmployeeService.addRole("CHANGE_PASSWORD"));
                userEmployeeService.setAuthentication(authentication);
                return new ModelAndView("redirect:/change-password");
            }

            if(tipo.equals("app")) {
                if( twoFactorService.verificationCode(userEmployee.get().getSecretKeyGoogleAuthenticator(),
                        codigo) ) {
                    authentication = getAuthentication(userEmployee.get());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return new ModelAndView("redirect:/home");
                } else {
                    return new ModelAndView("redirect:/authentication/"+userEmployee.get().getIdUser()+"?error=1&tipo" +
                            "=app");
                }
            } else if (tipo.equals("correo")) {
                Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getLastRegister(idUser);
                if(confirmationToken.isPresent()) {
                    if(confirmationToken.get().getToken().equals(codigo)) {
                        if(!confirmationToken.get().getExpiredAtToken().isBefore(LocalDateTime.now())) {
                            authentication = getAuthentication(userEmployee.get());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            confirmationTokenService.updateDateConfiramtionToken(confirmationToken.get().getIdToken()
                                    , LocalDateTime.now());
                            return new ModelAndView("redirect:/home");
                        } else {
                            return new ModelAndView("redirect:/authentication/" + userEmployee.get().getIdUser() +
                                    "?error=2&tipo=correo");
                        }
                    } else {
                        return new ModelAndView("redirect:/authentication/" + userEmployee.get().getIdUser() +
                                "?error=1&tipo=correo");
                    }
                }
                return new ModelAndView("redirect:/options");
            } else if (tipo.equals("sms")){
                return new ModelAndView("redirect:/options");
            } else {
                return null;
            }

        } else {
            return new ModelAndView("redirect:/authentication");
        }
    }

    @PostMapping("api/getMethodsAuthentication")
    public UserEmployee getMethodsAuthentication(Authentication authentication) {
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        return userEmployee.orElseGet(UserEmployee::new);
    }


    @Bean
    public HttpMessageConverter<BufferedImage> imageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    private Authentication getAuthentication(UserEmployee userEmployee){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userEmployee.getUsername(),
                userEmployee.getPassword(),
                userEmployee.getAuthorities()
        );
        return authentication;
    }

    private String createBodyEmailConfirmationToken(String token) {
        return "<h1>Codigo de Verificacion</h1>" +
                "<p>Su codigo de es " + token +
                " El codigo expira en 15 minutos";
    }
}
