package com.example.demo.user;

import com.example.demo.login.LoginController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

<<<<<<< HEAD
import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeService;

import java.util.Optional;
=======
import java.util.HashMap;
import java.util.Map;

>>>>>>> b6bd2fbd25184d0731dc3ea2ed76678c49a4afd2
@RestController
@RequestMapping("/")
public class UserEmployeeController {
    private final UserEmployeeService userEmployeeService;
    private final LoginController loginController;

    public UserEmployeeController(LoginController loginController,
                                  UserEmployeeService userEmployeeService) {
        this.loginController = loginController;
        this.userEmployeeService = userEmployeeService;
    }

	
	//este lo acabo de agregar
	private final EmployeeService employeeService=null;
	
    @GetMapping("home")
    public ModelAndView homeView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }

    @GetMapping("change-password")
    public ModelAndView changePassword(@RequestParam(name = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(error!=null) {
            if(error.equals("1")) {
                errors.put("errorLength", "La contraseña deber ser igual o mayor a 12 caracteres");
            }
            if(error.equals("2")) {
                errors.put("errorNoEquals", "Las contraseña no coinciden");
            }

        }
        model.addObject("errors", errors);
        model.setViewName("/password/changePassword");
        return model;
    }

    @PostMapping("change-password-check")
    public ModelAndView changePasswordCheck(Authentication authentication,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "passwordVerified") String passwordVerified) {
        if(password.length()<12) {
            return new ModelAndView("redirect:/change-password?error=1");
        }
        if(!password.equals(passwordVerified)) {
            return new ModelAndView("redirect:/change-password?error=2");
        }
        int response = userEmployeeService.updatePassword(password, authentication.getName());
        if(response == 1) {
            SecurityContextHolder.clearContext();
            return new ModelAndView("redirect:/login");
        } else {
            throw new IllegalStateException("No se ha podido actualizar el password");
        }
    }
    
    @GetMapping("registration")
    public ModelAndView viewRegister() {
    	//prueba que hice en la noche
    String email= "juanacostab.m.555@gmail.com";
    Optional<Employee>employee= employeeService.findByEmail(email);
    System.out.print(employee.toString());
    //prueba que hice en la noche
    return new ModelAndView("register");
    }
    
    @PostMapping("register-check")
    public ModelAndView viewCheckRegister()
    {
    	ModelAndView model= new ModelAndView();
    	return model ;
    }
    
}
