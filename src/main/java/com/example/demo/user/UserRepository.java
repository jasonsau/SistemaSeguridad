package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEmployee, Long> {

    @Query(value = "SELECT u FROM UserEmployee u WHERE u.employee.emailEmployee = ?1")
    Optional<UserEmployee> findByEmail(String email);
}
