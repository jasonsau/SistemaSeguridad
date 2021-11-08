package com.example.demo.state;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.example.demo.user.UserEmployee;

@Entity
public class StateHistory {
	//Attribute
	@Id
    @SequenceGenerator(
            name = "state_history_sequence",
            initialValue = 1,
            sequenceName = "state_history_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "state_history_sequence"
    )
	private Long id;
	private LocalDateTime startDate;
    private LocalDateTime endingDate;
    
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_user_state_history"
    )
    	private UserEmployee userEmployee;
    	private String userEmployeeStateHistory;
    public StateHistory(LocalDateTime startDate,
    		            UserEmployee userEmployee,
    		            String userEmployeeStateHistory) {
    	this.startDate= startDate;
    	this.userEmployee= userEmployee;
    	this.userEmployeeStateHistory=userEmployeeStateHistory;
    }
    
    public StateHistory(){}
    
    //Getters and Setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(LocalDateTime endingDate) {
		this.endingDate = endingDate;
	}

	public UserEmployee getUserEmployee() {
		return userEmployee;
	}

	public void setUserEmployee(UserEmployee userEmployee) {
		this.userEmployee = userEmployee;
	}

	public String getUserEmployeeStateHistory() {
		return userEmployeeStateHistory;
	}

	public void setUserEmployeeStateHistory(String userEmployeeStateHistory) {
		this.userEmployeeStateHistory = userEmployeeStateHistory;
	}

   
    
}
