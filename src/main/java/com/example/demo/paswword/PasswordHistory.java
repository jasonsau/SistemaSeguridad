package com.example.demo.paswword;

import com.example.demo.user.UserEmployee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


import java.time.LocalDateTime;

@Entity
public class PasswordHistory {

    //Attributes
    @Id
    @SequenceGenerator(
            name = "password_history_sequence",
            initialValue = 1,
            sequenceName = "password_history_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_history_sequence"
    )
    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_user_password_history"
    )
    private UserEmployee userEmployee;
    private String userEmployeePasswordHistory;

    public PasswordHistory(LocalDateTime createAt,
                           LocalDateTime expiredAt,
                           UserEmployee userEmployee,
                           String userEmployeePasswordHistory) {
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.userEmployee = userEmployee;
        this.userEmployeePasswordHistory = userEmployeePasswordHistory;
    }
    public PasswordHistory() {}

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UserEmployee getUserEmployee() {
        return userEmployee;
    }

    public void setUserEmployee(UserEmployee userEmployee) {
        this.userEmployee = userEmployee;
    }

    public String getUserEmployeePasswordHistory() {
        return userEmployeePasswordHistory;
    }

    public void setUserEmployeePasswordHistory(String userEmployeePasswordHistory) {
        this.userEmployeePasswordHistory = userEmployeePasswordHistory;
    }
}
