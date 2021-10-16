package com.example.demo.address;

import com.example.demo.municipality.Municipality;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Address {

    @Id
    @SequenceGenerator(
            name = "address_sequence",
            initialValue = 1,
            sequenceName = "address_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String streetAddress;
    @Column(
            nullable = false
    )
    private String neighbourhoodAddress;
    @Column(
            nullable = false
    )
    private int numberHouse;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_municipality_address"
    )
    private Municipality nameMunicipality;

    public Address(String streetAddress,
                   String neighbourhoodAddress,
                   int numberHouse,
                   Municipality nameMunicipality) {

        this.streetAddress = streetAddress;
        this.neighbourhoodAddress = neighbourhoodAddress;
        this.numberHouse = numberHouse;
        this.nameMunicipality = nameMunicipality;
    }
}
