package com.example.demo.departament;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Departament {

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

    public Departament(String nameDepartament) {
        this.nameDepartament = nameDepartament;
    }
}
