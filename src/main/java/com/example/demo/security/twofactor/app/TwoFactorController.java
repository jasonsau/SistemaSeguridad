package com.example.demo.security.twofactor.app;

import com.example.demo.Email;
import com.example.demo.responsebody.ResponsebodyCode;
import com.example.demo.security.twofactor.email.ConfirmationToken;
import com.example.demo.security.twofactor.email.ConfirmationTokenService;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    @GetMapping(value = "create-secret-key")
    public Map<String, String> createSecretKey(Authentication authentication) {
        Map<String, String> messages = new HashMap<>();
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        int response = 0;
        if(userEmployee.isPresent()) {
            String secretKey = twoFactorService.generateSecretKey();
            response = userEmployeeService.updateSecretKey(secretKey, userEmployee.get().getIdUser());
            if(response == 1) {
                messages.put("message", "Creado");
            } else {
                messages.put("message", "No Creado");
            }
        }
        return messages;
    }

    @GetMapping(value = "options")
    public ModelAndView optionsAuthentication(Authentication authentication) {
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());

        if(userEmployee.isPresent()) {
            if(userEmployee.get().isDoubleAuthenticator()) {
                return new ModelAndView("authentication/authenticationOption");
            }
            if(userEmployee.get().isBlocked()) {
                authentication = userEmployeeService.getAuthentication(userEmployee.get().getUsername(),
                        userEmployee.get().getPassword(),
                        userEmployeeService.addRole("CHANGE_PASSWORD"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return new ModelAndView("redirect:/change-password");
            }
            return new ModelAndView("redirect:/home");
        } else {
             throw new IllegalStateException("Error el usuario no se encuentra");
        }
    }

    @GetMapping(value = "authentication/{idUser}" )
    public ModelAndView verificationCodeView(@RequestParam(name = "error", required = false) String error,
                                             @PathVariable(name = "idUser", required = false) Long idUser,
                                             @RequestParam(name = "tipo") String tipo,
                                             Authentication authentication) {
        System.out.println(authentication.getAuthorities());
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
                                twoFactorService.createBodyEmailConfirmationToken(confirmationToken.getToken()),
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
        System.out.println(authentication.getAuthorities());

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
                    if(twoFactorService.redirectChangePasswordTempory(userEmployee.get())) {
                        return new ModelAndView("redirect:/change-password");
                    }
                    authentication = userEmployeeService.getAuthentication(userEmployee.get());
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
                            if(twoFactorService.redirectChangePasswordTempory(userEmployee.get())) {
                                return new ModelAndView("redirect:/change-password");
                            }
                            authentication = userEmployeeService.getAuthentication(userEmployee.get());
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

    @PostMapping("api/getMethods2Fac")
    public UserEmployee getMethods2Fac(Authentication authentication) {
        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        return userEmployee.orElseGet(UserEmployee::new);
    }

    @GetMapping("/add-2fac")
    public ModelAndView addMethod() {
        return new ModelAndView("/authentication/addMethod");
    }

    @PostMapping("api/verification-code-app")
    public Map<String, String> verificationCodeApp(@RequestBody ResponsebodyCode responsebodyCode,
                                   Authentication authentication) {
        Map<String, String> messages = new HashMap<>();
        boolean response;

        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        if (userEmployee.isPresent()) {
            response = twoFactorService.verificationCode(userEmployee.get().getSecretKeyGoogleAuthenticator(),
                    responsebodyCode.getCode());

            if (response) {
                boolean resp = verifiedMethods(userEmployee.get());
                int res = twoFactorService.updateDoubleApp(true, userEmployee.get().getIdUser());
                if(!resp) {
                    twoFactorService.uddateDoubleAuthenticator(true, userEmployee.get().getIdUser());
                }
                if (res == 1) {
                    messages.put("messageSend", "creado");
                }
            } else {
                messages.put("messageSend", "NoValido");
            }
        }
        return messages;
    }

    @Bean
    public HttpMessageConverter<BufferedImage> imageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    private boolean verifiedMethods(UserEmployee userEmployee) {
        int contador = 0;
        if(userEmployee.isDoubleAuthenticationEmail()) {
            contador+=1;
        }
        if(userEmployee.isDoubleAuthenticationApp()) {
            contador+=1;
        }
        if(userEmployee.isDoubleAuthenticationSms()){
            contador+=1;
        }

        return contador > 0;
    }

}
