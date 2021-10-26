package com.example.demo.employee;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService{
	
	public final EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
			this.employeeRepository= employeeRepository;
	}
	
	public Optional<Employee> retrieveEmployee(String email) {
		return employeeRepository.findByEmail(email);
	}
	
	
}
