package com.example.demo.user;

import com.example.demo.login.LoginController;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.api.email.rest.SendEmailService;
import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeService;

import java.util.Optional;
import java.util.StringTokenizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/")
public class UserEmployeeController {
    private final UserEmployeeService userEmployeeService;
    private final LoginController loginController;
    private final EmployeeService employeeService;

    public UserEmployeeController(LoginController loginController,
                                  UserEmployeeService userEmployeeService,
                                  EmployeeService employeeService) {
        this.loginController = loginController;
        this.userEmployeeService = userEmployeeService;
        this.employeeService= employeeService;
    }
    @Autowired
    private SendEmailService  sendEmailSender;
    
    

	
	
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
    
 
	@GetMapping("register")
    public ModelAndView viewRegister(@RequestParam(name = "error", required = false) String error) { 	
    	 Map<String, String> errors = new HashMap<>();
         if(error!=null) {
             if(error.equals("1")) {
                 errors.put("errorUserNotExist", "El empleado no se encuentra registrado, o el correo esta mal escirto");
             }
             if(error.equals("2")) {
                 errors.put("errorFirstName", "Los nombres no corresponden a ningun registro de empleado");
             } 
             if(error.equals("3")) {
            	 errors.put("errorLastName", "Los apellidos no corresponde a ningun registro de empleado");
             }
             if(error.equals("4")) {
            	 errors.put("errorCellphone", "El numero de telefono no corresponde a ningun registro de empleado");
             }
             if(error.equals("5")) {
            	 errors.put("errorExistUser", "El empleado ya se encuentra registrado. Vuelva a la pagina de login e inicie sesion");
             }
             if(error.equals("6")) {
            	 errors.put("emptyPasscode","Se debe ingrear un passcode de 4 digitos");
             }
         }
     ModelAndView model= new ModelAndView("register");
     model.addObject(errors);
    return model;
    }
    
    
    @PostMapping("register-check")
    public ModelAndView viewCheckRegister(@RequestParam(name="employee_name")String employee_name,
    		@RequestParam(name="employee_last_name")String employee_last_name,
    		@RequestParam(name="employee_email")String employee_email,
    		//@RequestParam(name="employee_date_birthday")LocalDate employee_date_birthday,
    		@RequestParam(name="employee_cellphone")String employee_cellphone,
    		@RequestParam(name="employee_passcode") String employee_passcode)
    {
    	Optional<Employee> employee= employeeService.retrieveEmployee(employee_email); 
    	Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(employee_email);
    	if(!employee.isPresent()) {
            return new ModelAndView("redirect:/register?error=1");
        }
    	if(!employee.get().getFirstNameEmployee().toLowerCase().equals(String.valueOf(employee_name).toLowerCase())){
    		System.out.println("El nombre guardado es:"+" "+employee.get().getFirstNameEmployee());
    		System.out.println("El nombre recivido es"+" "+employee_name);
            return new ModelAndView("redirect:/register?error=2");
        }
    	if(!employee.get().getLastNameEmployee().toLowerCase().equals(String.valueOf(employee_last_name).toLowerCase())) {
            
    		return new ModelAndView("redirect:/register?error=3");
        } 
    	if(!employee.get().getCellPhoneEmployee().equals(employee_cellphone)) {
    		System.out.println("El numero guardado es:"+" "+employee.get().getCellPhoneEmployee());
    		System.out.println("El numreo recivido es"+" "+employee_cellphone);
            return new ModelAndView("redirect:/register?error=4");
        }
    	if(userEmployee.isPresent()) {
            return new ModelAndView("redirect:/register?error=5");
        }
    	if(employee_passcode=="") {
    		return new ModelAndView("redirect:/register?error=6");
    	}
    	
    	
    		ModelAndView model= new ModelAndView();
        	
        	
			String employeeUser="";
	    	String tmpWord;
	    	StringTokenizer words= new StringTokenizer(employee_last_name);
	    	Date passwordExpiredAt= new Date();
	    	LocalDate localDate= LocalDate.now() ;
	    	
	    	//Genera una parte de lo que sera el nombre de usuario
			String characters = "0123456789";
			String employeeUser_1 = RandomStringUtils.random( 5, characters );
			
	    	
	    	//cuerpo del mensaje
			String body;
			//este de aqui puede dar error
			UserEmployee newUser= new UserEmployee();
			
			//userEmployee.get().setEmployee(employee.get());//aqui voy cheles
			
			 			
			//crear nombre de usuario    		    	
		    	while (words.hasMoreTokens()) {
		    	   tmpWord= words.nextToken();
		    	   employeeUser+=tmpWord.substring(0, 1);
		    	}
		    	employeeUser+= employeeUser_1;
		    
		    	
		    //llenar los campos del nuevo objeto userEmployee
		    	newUser.setEmployee(employee.get());
    			newUser.setPasswordUserEmployee(employee_passcode);
    			newUser.setTemporaryPassword(true);   
    			newUser.setUserNameEmployee(employeeUser);
    			newUser.setPasswordExpiredAt(localDate);
    			newUser.setEnabled(true);
    			newUser.setUserRole(UserRole.USER);
    			newUser.setAttempts(0);
    			newUser.setIsDoubleAuthenticator(false);
    			newUser.setSecretKeyGoogleAuthenticator("");
    			
    			userEmployeeService.saveUserEmployee(newUser);
    			
    			//enviar mensajes con credenciales
   			body="Su usuario es:"+" "+newUser.getUsername()+"\n"+"Su contraseña temporal es:"+" "
    			+newUser.getPassword()+"\n"+"Inicie sesiòn con su usuario y contraseña, al entrar por  "
    					+ "primera vez debe cambiar la contraseña. Ingrese al siguiente link para iniciar sesion: \n HTTP://localhost:8080/login";
    			sendEmailSender.sendMail("juanacostab.m.555@gmail.com", employee.get().getEmailEmployee(), "Registro de usuario", body);
    			model.setViewName("register-check"); 
    			model.addObject("newUser",newUser);
    			
	
return model;
    		
        
    	
    	
    }
    
}
