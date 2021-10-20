package com.example.demo.security.twofactor;

import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
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
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TwoFactorGoogleAuthenticatorController {
    private final String ISSUER_COMPANY = "Sistema seguridad Recursos Humanos";
    private final TwoFactorGoogleAuthenticatorService twoFactorGoogleAuthenticatorService;
    private final UserEmployeeService userEmployeeService;

    @GetMapping(value = "barCode", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage barCode(Authentication authentication) {

        Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
        if(userEmployee.isPresent()) {
            return twoFactorGoogleAuthenticatorService.createQRCode(
                    twoFactorGoogleAuthenticatorService.getGoogleAuthenticationBarCode(
                            userEmployee.get().getSecretKeyGoogleAuthenticator(),
                            userEmployee.get().getUserNameEmployee(),
                            ISSUER_COMPANY),
                    "png",
                    200,
                    200);
        }
        return null;
    }


    @GetMapping(value = "authentication/{idUser}" )
    public ModelAndView verificationCodeView(@RequestParam(name = "error", required = false) String error,
                                             @PathVariable(name = "idUser", required = false) String idUser) {
        ModelAndView model = new ModelAndView();
        model.addObject("idUser", idUser);
        if(error!=null) {
            model.addObject("error", "El codigo es incorrecto");
        }
        model.setViewName("/password/googleAuthenticator");
        return model;
    }

    @PostMapping("verification-code")
    public ModelAndView verificationCode(@RequestParam(name = "codigo", required = true) String codigo,
                                         @RequestParam(name = "idUser", required = true) Long idUser) {

        Optional<UserEmployee> userEmployee = userEmployeeService.findByIdUser(idUser);
        if(userEmployee.isPresent()) {
            if( twoFactorGoogleAuthenticatorService.verificationCode(userEmployee.get().getSecretKeyGoogleAuthenticator(),
                    codigo) ) {
                Authentication authentication = getAuthentication(userEmployee.get());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return new ModelAndView("redirect:/home");
            } else {
                return new ModelAndView("redirect:/authentication/"+userEmployee.get().getIdUser()+"?error");
            }
        } else {
            return new ModelAndView("redirect:/authentication");
        }
    }


    @Bean
    public HttpMessageConverter<BufferedImage> imageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
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
