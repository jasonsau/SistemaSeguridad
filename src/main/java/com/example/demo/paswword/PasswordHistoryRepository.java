package com.example.demo.paswword;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
	@Query(value = "SELECT u FROM PasswordHistory u WHERE u.userEmployee.idUser = ?1")
    ArrayList<PasswordHistory> findByUserId(long user_id);
}
