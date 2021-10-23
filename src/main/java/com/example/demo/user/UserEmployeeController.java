package com.example.demo.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeService;

import java.util.Optional;
@RestController
@RequestMapping("/")
public class UserEmployeeController {

	
	//este lo acabo de agregar
	private final EmployeeService employeeService=null;
	
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
