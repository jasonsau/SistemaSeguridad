package com.example.demo.departament;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentService {

    public DepartamentRepository departamentRepository;

    public DepartamentService(DepartamentRepository departamentRepository){
        this.departamentRepository=departamentRepository;
    }

    public List<Departament> getAll(){
        return departamentRepository.findAll();
    }

    public Optional<Departament> findById(Long id){
        return departamentRepository.findById(id);
    }
}
