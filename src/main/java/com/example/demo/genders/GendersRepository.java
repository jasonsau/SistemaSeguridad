package com.example.demo.genders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GendersRepository extends JpaRepository<Genders, Long> {
}
