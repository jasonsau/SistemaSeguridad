package com.example.demo.security.twofactor.email;

import com.example.demo.user.UserEmployee;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "idToken_sequence",
            initialValue = 1,
            sequenceName = "idToken_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "idToken_sequence"
    )
    private Long idToken;

    private String token;
    private LocalDateTime createAtToken;
    private LocalDateTime expiredAtToken;
    private LocalDateTime confirmationAtToken;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_token_employee"
    )
    private UserEmployee userEmployee;

    //Constructors
    public ConfirmationToken(String token,
                             LocalDateTime createAtToken,
                             LocalDateTime expiredAtToken,
                             UserEmployee userEmployee) {
        this.token = token;
        this.createAtToken = createAtToken;
        this.expiredAtToken = expiredAtToken;
        this.userEmployee = userEmployee;
    }

    public ConfirmationToken() { }


    //Getters and Setters
    public Long getIdToken(){
        return this.idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreateAtToken() {
        return createAtToken;
    }

    public void setCreateAtToken(LocalDateTime createAtToken) {
        this.createAtToken = createAtToken;
    }

    public LocalDateTime getExpiredAtToken() {
        return expiredAtToken;
    }

    public void setExpiredAtToken(LocalDateTime expiredAtToken) {
        this.expiredAtToken = expiredAtToken;
    }

    public UserEmployee getUserEmployee() {
        return userEmployee;
    }

    public void setUserEmployee(UserEmployee userEmployee) {
        this.userEmployee = userEmployee;
    }

    public LocalDateTime getConfirmationAtToken() {
        return confirmationAtToken;
    }

    public void setConfirmationAtToken(LocalDateTime confirmationAtToken) {
        this.confirmationAtToken = confirmationAtToken;
    }
}
