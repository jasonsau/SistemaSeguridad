package com.example.demo.genders;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GendersService {
    private final GendersRepository gendersRepository;

    public GendersService(GendersRepository gendersRepository) {
        this.gendersRepository = gendersRepository;
    }

    public List<Genders> getAllGender() {
        return gendersRepository.findAll();
    }

}
