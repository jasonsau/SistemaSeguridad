package com.example.demo.employee;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	//esto lo acabo de hacer y es para recuperar un empleado
	
	
	//lo agregue y se supone que busca a un empleado por su email y retorna su valor
	
	@Query(nativeQuery=false, value="Select e From Employee e WHERE e.emailEmployee= ?1")
	Optional<Employee> findByEmail(String email);

	//@Query(nativeQuery = false, value = "delete from Employee e Where e.duiEmployee= ?1")
	//Optional<Employee> deleteByDui(String dui);

	@Query("Select e from Employee e where e.duiEmployee=?1")
	List<Employee> findByDui(String dui);

}
