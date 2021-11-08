package com.example.demo.state;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface StateHistoryRepository extends JpaRepository<StateHistory, Long> {

	@Query(value="Select u From StateHistory u WHERE u.userEmployee.userNameEmployee= ?1")
	 ArrayList<StateHistory> findByUserNameEmployee(String userNameEmployee);

	@Query(value = "SELECT *FROM state_history order by start_date desc limit 1", nativeQuery = true)
	Optional<StateHistory> getLastRegister();

	@Modifying
	@Query("UPDATE StateHistory s SET s.endingDate = ?1 WHERE s.id = ?2")
	int updateUnlockedHistory(LocalDateTime endingDate, Long id);
}
