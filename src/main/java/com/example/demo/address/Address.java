package com.example.demo.address;

import com.example.demo.municipality.Municipality;

import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GenerationType;


@Entity
public class Address {

    //Attributes
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

    //Constructors
    public Address(String streetAddress,
                   String neighbourhoodAddress,
                   int numberHouse,
                   Municipality nameMunicipality) {

        this.streetAddress = streetAddress;
        this.neighbourhoodAddress = neighbourhoodAddress;
        this.numberHouse = numberHouse;
        this.nameMunicipality = nameMunicipality;
    }
    public Address() {}

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getNeighbourhoodAddress() {
        return neighbourhoodAddress;
    }

    public void setNeighbourhoodAddress(String neighbourhoodAddress) {
        this.neighbourhoodAddress = neighbourhoodAddress;
    }

    public int getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(int numberHouse) {
        this.numberHouse = numberHouse;
    }

    public Municipality getNameMunicipality() {
        return nameMunicipality;
    }

    public void setNameMunicipality(Municipality nameMunicipality) {
        this.nameMunicipality = nameMunicipality;
    }


}
