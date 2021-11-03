package com.example.demo.state;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class StateHistoryService {
	private final StateHistoryRepository stateHitoryRepository;
	
	public StateHistoryService(StateHistoryRepository stateHistoryRepository) {
		this.stateHitoryRepository= stateHistoryRepository;
	}
	
	public ArrayList<StateHistory> findByUserNameEmployee(String userNameEmployee){
		return stateHitoryRepository.findByUserNameEmployee(userNameEmployee);
	}
}
