package com.example.demo.employee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.address.Address;
import com.example.demo.departament.Departament;
import com.example.demo.departament.DepartamentService;
import com.example.demo.employee.*;
import com.example.demo.genders.Genders;
import com.example.demo.municipality.Municipality;
import com.example.demo.municipality.MunicipalityService;
import com.example.demo.paswword.PasswordHistory;
import com.example.demo.paswword.PasswordHistoryService;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeController;
import com.example.demo.user.UserEmployeeService;
import com.example.demo.workstation.WorkStation;

import com.example.demo.workstation.WorkStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")

public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private UserEmployeeService userEmployeeService;
	@Autowired
	private PasswordHistoryService passwordHistoryService;
	@Autowired
	private DepartamentService departamentService;
	@Autowired
	private MunicipalityService municipalityService;


	private UserEmployeeController userEmployeeController;
	
	private static final String INDEX_VIEW="index";

    /*@GetMapping("/employee/index")
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView(INDEX_VIEW);
        model.setViewName("/employee/index");
        List<Employee> empleados=employeeService.getAll();
		model.addObject("empleados", empleados);
        return model;
    }*/

	@GetMapping("/create")
	public ModelAndView create() {
		List<Departament> departaments = departamentService.getAll();
		List<Municipality> municipalities = municipalityService.getAll();
		ModelAndView model= new ModelAndView("/create");
		model.addObject("departaments", departaments);
		model.addObject("municipalities", municipalities);
		return model;
	}

	/*@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		return "create";
	}*/

	@PostMapping("/save")
	public ModelAndView save(@RequestParam(name = "employee_name") String firstNameEmployee,
					   @RequestParam(name = "employee_last_name") String lastNameEmployee,
					   @RequestParam(name = "employee_dui") String duiNameEmployee,
					   @RequestParam(name = "employee_nit") String nitEmployee,
					   @RequestParam(name = "employee_isss") String isssEmployee,
					   @RequestParam(name = "employee_nup") String nupEmployee,
					   @RequestParam(name = "employee_email") String emailEmployee,
					   @RequestParam(name = "employee_cell_phone") String cellPhoneEmployee,
					   @RequestParam(name = "employee_work_station") WorkStation workStationEmployee,
					   @RequestParam(name = "employee_genders") Genders gendersEmployee,
					   @RequestParam(name = "employee_address") Address addressEmployee,
					   @RequestParam(name = "employee_date_birth") String dateBirthEmployee) {

		LocalDate dateBirth=LocalDate.parse(dateBirthEmployee);
		ModelAndView model= new ModelAndView();
		Employee newEmployee=new Employee();
		newEmployee.setFirstNameEmployee(firstNameEmployee);
		newEmployee.setLastNameEmployee(lastNameEmployee);
		newEmployee.setDuiEmployee(duiNameEmployee);
		newEmployee.setNitEmployee(nitEmployee);
		newEmployee.setIsssEmployee(isssEmployee);
		newEmployee.setNupEmployee(nupEmployee);
		newEmployee.setEmailEmployee(emailEmployee);
		newEmployee.setCellPhoneEmployee(cellPhoneEmployee);
		newEmployee.setWorkStationEmployee(workStationEmployee);
		newEmployee.setGendersEmployee(gendersEmployee);
		newEmployee.setAddressEmployee(addressEmployee);
		newEmployee.setDateBirth(dateBirth);

		employeeService.save(newEmployee);
		model.setViewName("/save");
		model.addObject("newEmployee", newEmployee);
		return model;

	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String idEmployee, Model model){
		Long id = Long.parseLong(idEmployee);
		try {
			passwordHistoryService.deleteByIdEmployee(id);
			userEmployeeService.deleteByIdEmployee(id);
			employeeService.delete(id);
		}catch (Exception e){
		}
		return "redirect:/employee/index";
	}



}
