package com.example.demo.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.user.UserEmployee;


@Service
public class EmployeeService{
	
	public final EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
			this.employeeRepository= employeeRepository;
	}

	public Optional<Employee> retrieveEmployee(String email) {
		return employeeRepository.findByEmail(email);
	}

	public Employee create(Employee e){
		return this.employeeRepository.save(e);
	}
	public Employee save(Employee e) {
		return employeeRepository.save(e);
	}
	
	public Optional<Employee> listByDui(String dui){
		return retrieveEmployee(dui);
	}
	
    public List<Employee> getAll(){
    	return employeeRepository.findAll();
    }

	/*public void delete(String dui){
		employeeRepository.deleteByDui(dui);
	}*/

	public boolean delete(Long id){
		this.employeeRepository.deleteById(id);
		return true;
	}
}
