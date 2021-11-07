package com.example.demo.departament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long> {
}
