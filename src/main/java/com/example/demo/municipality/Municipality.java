package com.example.demo.municipality;


import com.example.demo.departament.Departament;

import javax.persistence.*;

@Entity
public class Municipality {

    //Attributes
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

    //Constructors
    public Municipality(String nameMunicipality,
                        Departament nameDepartament) {
        this.nameMunicipality = nameMunicipality;
        this.nameDepartament = nameDepartament;
    }
    public Municipality() {}

    //Getters and Setters
    public Long getIdMunicipality() {
        return idMunicipality;
    }

    public String getNameMunicipality() {
        return nameMunicipality;
    }

    public void setNameMunicipality(String nameMunicipality) {
        this.nameMunicipality = nameMunicipality;
    }

    public Departament getNameDepartament() {
        return nameDepartament;
    }

    public void setNameDepartament(Departament nameDepartament) {
        this.nameDepartament = nameDepartament;
    }
}
