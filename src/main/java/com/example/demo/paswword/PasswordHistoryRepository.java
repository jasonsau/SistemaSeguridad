package com.example.demo.paswword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    @Query(value = "SELECT *FROM password_history "+
            "WHERE id_user_password_history = ?1 " +
            "order by create_at desc " +
            "LIMIT 3",
    nativeQuery = true)
    List<PasswordHistory> findLastThreeRegister(Long idUser);
}
