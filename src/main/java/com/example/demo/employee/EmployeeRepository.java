package com.example.demo.employee;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	//esto lo acabo de hacer y es para recuperar un solo emppeado
	
	 @Query("SELECT u FROM Employee u WHERE u.emailEmployee = ?1")
	    Optional<Employee> findByEmail(String email);
}
