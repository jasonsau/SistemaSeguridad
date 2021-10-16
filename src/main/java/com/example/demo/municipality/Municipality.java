package com.example.demo.municipality;


import com.example.demo.departament.Departament;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Municipality {

    @Id
    @SequenceGenerator(
            name = "municipality_sequence",
            initialValue = 1,
            sequenceName = "municipality_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "municipality_sequence"
    )
    private Long idMunicipality;
    @Column(
            nullable = false
    )
    private String nameMunicipality;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_departament_municipality"
    )
    private Departament nameDepartament;

    public Municipality(String nameMunicipality,
                        Departament nameDepartament) {
        this.nameMunicipality = nameMunicipality;
        this.nameDepartament = nameDepartament;
    }
}
