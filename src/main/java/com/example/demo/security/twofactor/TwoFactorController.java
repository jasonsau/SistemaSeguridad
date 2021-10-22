package com.example.demo.security.twofactor;

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
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TwoFactorController {
    private final String ISSUER_COMPANY = "Sistema seguridad Recursos Humanos";
    private final TwoFactorService twoFactorService;
    private final UserEmployeeService userEmployeeService;

    public TwoFactorController(TwoFactorService twoFactorService,
                               UserEmployeeService userEmployeeService) {
        this.twoFactorService = twoFactorService;
        this.userEmployeeService = userEmployeeService;
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
            if( twoFactorService.verificationCode(userEmployee.get().getSecretKeyGoogleAuthenticator(),
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
                userEmployee.getUsername(),
                userEmployee.getPassword(),
                userEmployee.getAuthorities()
        );
        return authentication;
    }
}
