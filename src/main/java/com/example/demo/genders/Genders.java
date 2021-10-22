package com.example.demo.genders;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;


@Entity
public class Genders {

    @Id
    @SequenceGenerator(
            name = "genders_sequence",
            initialValue = 1,
            sequenceName = "genders_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genders_sequence"
    )
    private Long idGenders;
    @Column(
            nullable = false
    )
    private String nameGenders;

    public Genders(String nameGenders) {
        this.nameGenders = nameGenders;
    }

    public Genders() {}

    //Getters and Setters

    public Long getIdGenders() {
        return idGenders;
    }

    public void setIdGenders(Long idGenders) {
        this.idGenders = idGenders;
    }

    public String getNameGenders() {
        return nameGenders;
    }

    public void setNameGenders(String nameGenders) {
        this.nameGenders = nameGenders;
    }
}
