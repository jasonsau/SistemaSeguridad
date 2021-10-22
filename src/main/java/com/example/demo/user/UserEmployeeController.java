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
    public ModelAndView homeView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }

    @GetMapping("password/changePassword")
    public ModelAndView changePassword() {
        return new ModelAndView("/password/changePassword");
    }
}
