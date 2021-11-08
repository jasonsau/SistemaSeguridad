package com.example.demo.employee;


import com.example.demo.address.Address;
import com.example.demo.genders.Genders;
import com.example.demo.workstation.WorkStation;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Employee {

    //Attributes
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
    private LocalDate dateBirth;

    //Constructors
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
                    Address addressEmployee,
                    LocalDate dateBirth) {
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
        this.dateBirth = dateBirth;
    }

    public Employee() {}

    //Getters and Setters

    public Long getIdEmployee() {
        return idEmployee;
    }

    public String getFirstNameEmployee() {
        return firstNameEmployee;
    }

    public void setFirstNameEmployee(String firstNameEmployee) {
        this.firstNameEmployee = firstNameEmployee;
    }

    public String getLastNameEmployee() {
        return lastNameEmployee;
    }

    public void setLastNameEmployee(String lastNameEmployee) {
        this.lastNameEmployee = lastNameEmployee;
    }

    public String getDuiEmployee() {
        return duiEmployee;
    }

    public void setDuiEmployee(String duiEmployee) {
        this.duiEmployee = duiEmployee;
    }

    public String getNitEmployee() {
        return nitEmployee;
    }

    public void setNitEmployee(String nitEmployee) {
        this.nitEmployee = nitEmployee;
    }

    public String getIsssEmployee() {
        return isssEmployee;
    }

    public void setIsssEmployee(String isssEmployee) {
        this.isssEmployee = isssEmployee;
    }

    public String getNupEmployee() {
        return nupEmployee;
    }

    public void setNupEmployee(String nupEmployee) {
        this.nupEmployee = nupEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }

    public String getCellPhoneEmployee() {
        return cellPhoneEmployee;
    }

    public void setCellPhoneEmployee(String cellPhoneEmployee) {
        this.cellPhoneEmployee = cellPhoneEmployee;
    }

    public WorkStation getWorkStationEmployee() {
        return workStationEmployee;
    }

    public void setWorkStationEmployee(WorkStation workStationEmployee) {
        this.workStationEmployee = workStationEmployee;
    }

    public Genders getGendersEmployee() {
        return gendersEmployee;
    }

    public void setGendersEmployee(Genders gendersEmployee) {
        this.gendersEmployee = gendersEmployee;
    }

    public Address getAddressEmployee() {
        return addressEmployee;
    }

    public void setAddressEmployee(Address addressEmployee) {
        this.addressEmployee = addressEmployee;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
}
