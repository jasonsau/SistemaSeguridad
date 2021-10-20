package com.example.demo.paswword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {


}
