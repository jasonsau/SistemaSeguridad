package com.example.demo.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class UserEmployeeController {

    @GetMapping("home")
    public ModelAndView homeView(Authentication auth) {
        String nombreUser = auth.getName();
        ModelAndView model = new ModelAndView();
        model.addObject("user",nombreUser);
        model.setViewName("home");
        return model;
    }
}
