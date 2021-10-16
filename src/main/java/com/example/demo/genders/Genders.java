package com.example.demo.genders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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
}
