package com.example.demo.security.twofactor.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query(value = "SELECT *FROM confirmation_token WHERE id_token_employee = ?1 ORDER BY id_token desc LIMIT 1",
    nativeQuery = true)
    Optional<ConfirmationToken> getLastRegister(Long idUser);

    @Modifying
    @Query("UPDATE ConfirmationToken  SET confirmationAtToken = ?2 WHERE idToken = ?1")
    void updateDateConfiramtionToken(Long id, LocalDateTime confirmationAtToken);

    @Query("SELECT t FROM ConfirmationToken t WHERE t.token = ?1")
    Optional<ConfirmationToken> findByToken(String token);

}