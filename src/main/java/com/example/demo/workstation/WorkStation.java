package com.example.demo.workstation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

}
