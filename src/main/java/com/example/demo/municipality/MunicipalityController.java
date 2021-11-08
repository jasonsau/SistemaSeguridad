package com.example.demo.municipality;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MunicipalityController {
    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }


    @GetMapping("/api/getMunicipality/{idDepartament}")
    public List<Municipality> getMunicipalitybyDepartament(@PathVariable("idDepartament") Long idDepartament){

        return municipalityService.findByDepartament(idDepartament);

    }
}
