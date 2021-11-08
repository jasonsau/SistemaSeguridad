package com.example.demo.departament;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

@Entity
public class Departament {

    //Attributes
    @Id
    @SequenceGenerator(
            name = "departament_sequence",
            initialValue = 1,
            sequenceName = "departament_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "departament_sequence"
    )
    private Long idDepartament;
    @Column(
            nullable = false
    )
    private String nameDepartament;

    //Constructors
    public Departament(String nameDepartament) {
        this.nameDepartament = nameDepartament;
    }
    public Departament() {}

    //Getters and Setters
    public Long getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Long idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getNameDepartament() {
        return nameDepartament;
    }

    public void setNameDepartament(String nameDepartament) {
        this.nameDepartament = nameDepartament;
    }

}
