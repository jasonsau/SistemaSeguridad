package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Long> {

    @Query(value = "SELECT u FROM UserEmployee u WHERE u.employee.emailEmployee = ?1")
    Optional<UserEmployee> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserEmployee u SET u.attempts = ?1 " + "WHERE u.idUser = ?2")
    int updateUserAttempts(int attempts, Long id);

    @Modifying
    @Query("UPDATE UserEmployee u SET u.blocked = true, u.enabled = false " + "WHERE u.idUser = ?1")
    void updateUserLocked(Long id);

    @Modifying
    @Query("UPDATE UserEmployee u SET u.attempts = 0" + " WHERE u.idUser = ?1")
    void restartAttempts(Long id);


    @Query("SELECT u FROM UserEmployee u WHERE u.userNameEmployee = ?1")
    Optional<UserEmployee> findByUsername(String username);
}
