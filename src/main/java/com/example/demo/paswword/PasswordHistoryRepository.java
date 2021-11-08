package com.example.demo.paswword;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



@Transactional
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
	@Query(value = "SELECT u FROM PasswordHistory u WHERE u.userEmployee.idUser = ?1")
    ArrayList<PasswordHistory> findByUserId(long user_id);


	@Query(value = "SELECT *FROM password_history "+
            "WHERE id_user_password_history = ?1 " +
            "order by create_at desc " +
            "LIMIT 3",
    nativeQuery = true)
    List<PasswordHistory> findLastThreeRegister(Long idUser);

    @Modifying
    @Query("DELETE FROM PasswordHistory u WHERE u.userEmployee.idUser = ?1")
    void deleteByIdEmployee(Long id);
   

}
