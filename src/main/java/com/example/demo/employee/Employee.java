package com.example.demo.employee;


import com.example.demo.address.Address;
import com.example.demo.genders.Genders;
import com.example.demo.workstation.WorkStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            initialValue = 1,
            sequenceName = "employee_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long idEmployee;
    private String firstNameEmployee;
    private String lastNameEmployee;
    @Column(
            unique = true,
            nullable = false
    )
    private String duiEmployee;
    @Column(
            unique = true,
            nullable = false
    )
    private String nitEmployee;
    @Column(
            unique = true,
            nullable = false
    )
    private String isssEmployee;
    @Column(
            unique = true,
            nullable = false
    )
    private String nupEmployee;

    @Column(
            unique = true,
            nullable = false
    )
    private String emailEmployee;

    @Column(
            unique = true,
            nullable = false
    )
    private String cellPhoneEmployee;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_workStation_employee"
    )
    private WorkStation workStationEmployee;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_genders_employee"
    )
    private Genders gendersEmployee;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_address_employee"
    )
    private Address addressEmployee;

    public Employee(String firstNameEmployee,
                    String lastNameEmployee,
                    String duiEmployee,
                    String nitEmployee,
                    String isssEmployee,
                    String nupEmployee,
                    String emailEmployee,
                    String cellPhoneEmployee,
                    WorkStation workStationEmployee,
                    Genders gendersEmployee,
                    Address addressEmployee) {
        this.firstNameEmployee = firstNameEmployee;
        this.lastNameEmployee = lastNameEmployee;
        this.duiEmployee = duiEmployee;
        this.nitEmployee = nitEmployee;
        this.isssEmployee = isssEmployee;
        this.nupEmployee = nupEmployee;
        this.emailEmployee = emailEmployee;
        this.cellPhoneEmployee = cellPhoneEmployee;
        this.workStationEmployee = workStationEmployee;
        this.gendersEmployee = gendersEmployee;
        this.addressEmployee = addressEmployee;
    }
}
