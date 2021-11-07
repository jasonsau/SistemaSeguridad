package com.example.demo.user;

import com.example.demo.Email;
import com.example.demo.paswword.PasswordHistoryService;
import org.apache.commons.lang3.RandomStringUtils;
import com.example.demo.responsebody.ResponseBodyUserEmail;
import com.example.demo.security.twofactor.email.ConfirmationToken;
import com.example.demo.security.twofactor.email.ConfirmationTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.api.email.rest.SendEmailService;
import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeService;
import com.example.demo.paswword.PasswordHistory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringTokenizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class UserEmployeeController {

    private final UserEmployeeService userEmployeeService;
    private final EmployeeService employeeService;
    private final ConfirmationTokenService confirmationTokenService;
	private static final String INDEX_VIEW="index";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SendEmailService  sendEmailSender;
    private final Email email;
    private final PasswordHistoryService passwordHistoryService;


    public UserEmployeeController(UserEmployeeService userEmployeeService,
                                  EmployeeService employeeService,
                                  ConfirmationTokenService confirmationTokenService,
                                  SendEmailService sendEmailSender,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  Email email,
                                  PasswordHistoryService passwordHistoryService) {
        this.userEmployeeService = userEmployeeService;
        this.employeeService = employeeService;
        this.confirmationTokenService = confirmationTokenService;
        this.sendEmailSender = sendEmailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.email = email;
        this.passwordHistoryService = passwordHistoryService;
    }


	
	
    @GetMapping("/homeAdmin")
    public ModelAndView homeView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("homeAdmin");
        return model;
    }

    @GetMapping("/employee/index")
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView(INDEX_VIEW);
        model.setViewName("/employee/index");
        List<Employee> empleados=employeeService.getAll();
		model.addObject("empleados",empleados);
        return model;
    }
    
    @GetMapping("change-password")
    public ModelAndView changePassword(@RequestParam(name = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(error!=null) {
            if(error.equals("1")) {
                errors.put("errorLength", "La contraseña deber tener una mayuscula, un numero y un caracter " +
                        "especial " +
                        "con 12 caracteres");
            }
            if(error.equals("2")) {
                errors.put("errorNoEquals", "Las contraseña no coinciden");
            }
            if(error.equals("3")) {
                errors.put("errorPasswordFound", "La contraseña coincide con las ultimas 3 elija otra");
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

        Optional<UserEmployee> userEmployeeT = userEmployeeService.findByUsername(authentication.getName());

        if(!userEmployeeService.validatePassword(password)) {
            return new ModelAndView("redirect:/change-password?error=1");
        }
        if(userEmployeeT.isPresent()) {
            if(passwordHistoryService.verifiedLastestPassword(password, userEmployeeT.get())) {
                return new ModelAndView("redirect:/change-password?error=3");
            }
        }
        if(!password.equals(passwordVerified)) {
            return new ModelAndView("redirect:/change-password?error=2");
        }
        int response = userEmployeeService.updatePassword(password, authentication.getName());
        if(response == 1) {
            Optional<UserEmployee> userEmployee = userEmployeeService.findByUsername(authentication.getName());
            if(userEmployee.isPresent()) {
                if(userEmployee.get().isBlocked()) {
                    userEmployeeService.updateUserUnlockedUser(userEmployee.get().getIdUser());
                }
            }
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
                 errors.put("errorFirstName", "El nombre no se encuentra");
             }
             if(error.equals("2")) {
                 errors.put("errorLastName", "Los apellidos no corresponden a ningun registro de empleado");
             } 
             if(error.equals("3")) {
            	 errors.put("errorEmail", "El email no corresponde a ningun registro de empleado");
             }
             if(error.equals("4")) {
            	 errors.put("errorDateBirth", "La fecha de nacimiento no corresponde a ningn registro");
             }
             if(error.equals("5")) {
            	 errors.put("errorCellPhone", "El numero de telefono no coincide con ningun registro");
             }
             if(error.equals("6")) {
            	 errors.put("emptyPasscode","Se debe ingrear un passcode de 4 digitos");
             }
         }
     ModelAndView model= new ModelAndView("register");
     model.addObject("errors",errors);
    return model;
    }
    
    
    @PostMapping("register-check")
    public ModelAndView viewCheckRegister(@RequestParam(name="employee_name")String employee_name,
    		@RequestParam(name="employee_last_name")String employee_last_name,
    		@RequestParam(name="employee_email")String employee_email,
    		@RequestParam(name="employee_date_birthday")String employee_date_birthday,
    		@RequestParam(name="employee_cellphone")String employee_cellphone,
    		@RequestParam(name="employee_passcode") String employee_passcode) {

        LocalDate dateBirth = LocalDate.parse(employee_date_birthday);
        Optional<Employee> employee= employeeService.retrieveEmployee(employee_email);
    	Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(employee_email);
    	if(!employee.isPresent()) {
            return new ModelAndView("redirect:/register?error=3");
        }
    	if(!employee.get().getFirstNameEmployee().toLowerCase().equals(String.valueOf(employee_name).toLowerCase())){
            return new ModelAndView("redirect:/register?error=1");
        }
    	if(!employee.get().getLastNameEmployee().toLowerCase().equals(String.valueOf(employee_last_name).toLowerCase())) {
            
    		return new ModelAndView("redirect:/register?error=2");
        } 
    	if(!employee.get().getCellPhoneEmployee().equals(employee_cellphone)) {
            return new ModelAndView("redirect:/register?error=5");
        }
    	if(userEmployee.isPresent()) {
            return new ModelAndView("redirect:/register?error=5");
        }
    	if(employee_passcode.isEmpty()) {
    		return new ModelAndView("redirect:/register?error=6");
    	}

        if(!employee.get().getDateBirth().equals(dateBirth)) {
            return new ModelAndView("redirect:/register?error=7");
        }
        ModelAndView model= new ModelAndView();

        String employeeUser="";
        String tmpWord;
        StringTokenizer words= new StringTokenizer(employee_last_name);

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
        newUser.setPasswordUserEmployee(bCryptPasswordEncoder.encode(employee_passcode));
        newUser.setTemporaryPassword(true);
        newUser.setUserNameEmployee(employeeUser);
        newUser.setPasswordExpiredAt(LocalDateTime.now().plusDays(30));
        newUser.setEnabled(false);
        newUser.setUserRole(UserRole.USER);
        newUser.setAttempts(0);
        newUser.setIsDoubleAuthenticator(false);
        newUser.setSecretKeyGoogleAuthenticator("");
        newUser.setDoubleAuthenticationEmail(false);

        userEmployeeService.saveUserEmployee(newUser);
        ConfirmationToken confirmationToken = confirmationTokenService.createNewTokenUnlocked(newUser);
        confirmationTokenService.insertConfirmationToken(confirmationToken);


            //enviar mensajes con credenciales
        body="Su usuario es:"+" "+newUser.getUsername()+"\n"+"Su contraseña temporal es:"+" "
            +employee_passcode+"\n"+"Inicie sesiòn con su usuario y contraseña, al entrar por  "
                    + "primera vez debe cambiar la contraseña. Ingrese al siguiente link para verificar su cuenta: \n"+
                "http://localhost:8080/enabled-user?token="+confirmationToken.getToken();
            sendEmailSender.sendMail("juanacostab.m.555@gmail.com", employee.get().getEmailEmployee(), "Registro de usuario", body);
            model.setViewName("register-check");
            model.addObject("newUser",newUser);

        return model;
    }

    @GetMapping("/users")
    public List<UserEmployee> selectUsers() {
        return userEmployeeService.selectUsers();
    }

    @PostMapping("/unlocked-user")
    public Map<String, String> unlockedUser(@RequestBody ResponseBodyUserEmail responseBodyUserEmail) {
        Map<String, String> messages = new HashMap<>();
        Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(responseBodyUserEmail.getEmail());

        if(userEmployee.isPresent()) {
            if(userEmployee.get().isBlocked()) {
                ConfirmationToken confirmationToken = confirmationTokenService
                        .createNewTokenUnlocked(userEmployee.get());
                confirmationTokenService.insertConfirmationToken(confirmationToken);
                email.sendEmailToken(userEmployee.get().getEmployee().getEmailEmployee(),
                        createBodyEmailUnlockedUser(confirmationToken.getToken()),
                        "Desbloquear Usuario");
                messages.put("message", "Se le ha enviado a su correo las indicaciones");
                messages.put("number", "1");
            } else {
                messages.put("message", "Su usuario no esta bloqueado");
                messages.put("number", "2");
            }
        } else {
            messages.put("message", "No se ha encontrado el usuario");
            messages.put("number", "3");
        }
        return  messages;
    }

    @GetMapping("/unlocked-user/check")
    public ModelAndView unlockedUserCheck(@RequestParam("token") String token) {
        Optional<ConfirmationToken>confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken.isPresent()) {
            UserEmployee userEmployee = confirmationToken.get().getUserEmployee();
            Authentication authentication = userEmployeeService.getAuthentication(userEmployee.getUsername(),
                    userEmployee.getPassword(),
                    userEmployeeService.addRole("AUTHENTICATOR"));
            userEmployeeService.setAuthentication(authentication);
        }
        return new ModelAndView("redirect:/options");

    }

    @GetMapping("/enabled-user")
    public String enabledUser(@RequestParam("token") String token) {
        Optional<ConfirmationToken>confirmationToken = confirmationTokenService.findByToken(token);
        int response = 0;
        if(confirmationToken.isPresent()) {
            response = userEmployeeService.enabledUser(confirmationToken.get().getUserEmployee().getIdUser());
        }
        if(response == 1) {
            return "<p>Se ha verificado su cuenta ingrese al siguiente link para hacer el primer inicio de Sesion</p>" +
                    "<a href = 'http://localhost:8080/login'>Iniciar Sesion</a>";
        } else {
            throw new IllegalStateException("Error no se ha podido verificar su usuario");
        }
    }

    private String createBodyEmailUnlockedUser(String token) {
        return "<h1>Instrucciones para desbloqueo</h1>"+
                "<p>Ingrese al siguiente link para seguir con el proceso</p>"+
                "http://localhost:8080/unlocked-user/check?token="+token;

    }
    
    //aqui empieza el codigo de las interfces que vera el usuario
    @GetMapping("/userAccount")
    public ModelAndView userAccount(Authentication authentication){
    	ModelAndView model= new ModelAndView();
    	//descomentar la instruccion de abajo para probar con el objeto authentication y comentar la segunda contando despues de esta linea
    	//Optional<UserEmployee> users = userEmployeeService.findByUsername(authentication.getName());
        Optional<UserEmployee> users = userEmployeeService.findByEmail("ae19001@ues.edu.sv");
        if(users.isPresent()) {
        	UserEmployee newUser= users.get();
        	model.setViewName("/account/userAccount");
        	model.addObject("userNameEmployee",newUser.getUsername());
        	model.addObject("userAdress",newUser.getEmployee().getAddressEmployee().toString());
        	model.addObject("userWorkstation", newUser.getEmployee().getWorkStationEmployee().getNameWorkStation());
        	model.addObject("userDui",newUser.getEmployee().getDuiEmployee());
        	model.addObject("userEmail", newUser.getEmployee().getEmailEmployee());
        	model.addObject("userCell", newUser.getEmployee().getCellPhoneEmployee());
        	model.addObject("nameEmployee", newUser.getEmployee().getFirstNameEmployee()+" "+newUser.getEmployee().getLastNameEmployee());
        	model.addObject("idUser",newUser.getIdUser());
        	model.addObject("userMunicipality",users.get().getEmployee().getAddressEmployee().getNameMunicipality().getNameMunicipality());
        	model.addObject("userStreet",users.get().getEmployee().getAddressEmployee().getStreetAddress());
            
        }
    	return model;
    }
 
    @GetMapping("/userStatistics")
    public ModelAndView userStatistics(@RequestParam(name="userNameEmployee", required= false) String userNameEmployee) {
    	ModelAndView model= new ModelAndView();
    	Optional<UserEmployee> users = userEmployeeService.findByUsername(userNameEmployee);
    	if(users.isPresent()) {
    		ArrayList<PasswordHistory> passwordHistory=  passwordHistoryService.findByIdUser(users.get().getIdUser());
    		if(!passwordHistory.isEmpty()) {
    		    PasswordHistory passHistory= passwordHistory.get(passwordHistory.size()-1);
    			model.setViewName("/account/userStatistics");
    			model.addObject("passwordChanges",passwordHistory.size());
            	model.addObject("userPasswordHistory", passHistory.getUserEmployee().getUsername());
            	model.addObject("passwordCreateAt",passHistory.getCreateAt());
            	model.addObject("passwordExpiredAt",passHistory.getExpiredAt());
            	if(passHistory.getCreateAt().getMonth()==passHistory.getExpiredAt().getMonth()) {
            	    model.addObject("daysToExpire",passHistory.getExpiredAt().getDayOfMonth()-passHistory.getCreateAt().getDayOfMonth());
            	}
            	else {
            	    model.addObject("daysToExpire",30-(passHistory.getExpiredAt().getDayOfMonth()-passHistory.getCreateAt().getDayOfMonth()));	
            	}
            	
            	return model;
    		}
    		model.setViewName("redirect:/userAccount");
    	 return model;
    	}
    	else {
    		model.setViewName("redirect:/userAccount");
    		return model;	
    	}
   }
    
  @GetMapping("/changePasswordUser")
  public ModelAndView changePasswordUser(@RequestParam(name="error", required=false)String error,
		  @RequestParam(name="userNameEmployee", required= false) String userNameEmployee){
	  ModelAndView model= new ModelAndView();
	  Map<String, String> errors = new HashMap<>();
	 if(error!=null) {
		 if(error.equals("1")) {
			  errors.put("usuarioNoExiste", "El usuario que ha ingresado es incorrecto");
		  }
		  else if(error.equals("2")) {
			  errors.put("longituIncorrecta","La contraseña tiene que tener minimo 12 caracteres");
		  }
		  else if(error.equals("3")) {
			  errors.put("contraseñasDistintas","las contraseña no coincide");
		  }
		  else if(error.equals("4")) {
			  errors.put("exito","Se cambio la contraeña correctamente");
		  }
	 }
	  model.setViewName("/account/changePassword");
	  model.addObject("errors",errors);
	  model.addObject("userNameEmployee",userNameEmployee);
	  return model;
  }
  
  @PostMapping("/changePassword/check-password")
  public ModelAndView checkChangePasswordUser(
		  @RequestParam(name="user")String user,
		  @RequestParam(name="password")String password,
		  @RequestParam(name="new_password")String new_password) {
	  Optional<UserEmployee> users = userEmployeeService.findByUsername(user);
	  BCryptPasswordEncoder newbCryptPasswordEncoder= new BCryptPasswordEncoder();
	  if(users.isPresent()) {
		  if(!(new_password.length()<12)) {
			  boolean condicion= newbCryptPasswordEncoder.matches(password, users.get().getPassword());
		  if(condicion) {
			  userEmployeeService.updatePassword(new_password, user);
			  return new ModelAndView("redirect:/changePasswordUser?error=4");
		  }
		  else {
			  return new ModelAndView("redirect:/changePasswordUser?error=3");
		  }
		}
		  else {
			return new ModelAndView("redirect:/changePasswordUser?error=2");  
		  }
	  }
	  else {
		  return new ModelAndView("redirect:/changePasswordUser?error=1");
	  }

  }
  
  //aqui termina el codigo para las interfazes y funciones del usuario 

//    @GetMapping("/home")
//    public ModelAndView inicio(Model model,@RequestParam(name="employee_name")String username) {
//		Optional<UserEmployee> empleados=this.userEmployeeService.findByUsername(username);
//		model.addAttribute("empleados",empleados);
//		return model;
//    }
}


