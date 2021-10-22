package com.example.demo.workstation;

import javax.persistence.*;

@Entity
public class WorkStation {

    @Id
    @SequenceGenerator(
            name = "workstation_sequence",
            sequenceName = "workstation_sequence",
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workstation_sequence"
    )
    private Long idWorkStation;
    @Column(
            nullable = false
    )
    private String nameWorkStation;

    public WorkStation(String nameWorkStation) {
        this.nameWorkStation = nameWorkStation;
    }

    public WorkStation() {}

    public Long getIdWorkStation() {
        return idWorkStation;
    }

    public String getNameWorkStation() {
        return nameWorkStation;
    }

    public void setNameWorkStation(String nameWorkStation) {
        this.nameWorkStation = nameWorkStation;
    }
}
