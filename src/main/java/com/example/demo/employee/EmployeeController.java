package com.example.demo.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.employee.*;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping

public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	//listar está en el controlador de usuario
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		return "create";
	}
	
	@PostMapping("/save")
	public String save(@Validated @ModelAttribute("employee") Employee employee, Model model) {
		employeeService.save(employee);
		return "redirect:/home";
	}
	
	@PostMapping("/editar")
	public String editar(@PathVariable String email, Model model) {
		Optional<Employee> employee=employeeService.employeeRepository.findByEmail(email);
		model.addAttribute("employee", employee);
		return "create";
	}
}
