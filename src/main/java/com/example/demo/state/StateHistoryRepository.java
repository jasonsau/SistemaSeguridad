package com.example.demo.state;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface StateHistoryRepository extends JpaRepository<StateHistory, Long> {

	@Query(value="Select u From StateHistory u WHERE u.userEmployee.userNameEmployee= ?1")
	 ArrayList<StateHistory> findByUserNameEmployee(String userNameEmployee);
}
