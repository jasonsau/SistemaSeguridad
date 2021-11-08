package com.example.demo.state;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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

	public void insertStateHistory(StateHistory stateHistory) {
		stateHitoryRepository.save(stateHistory);
	}
	public int updateUnlockedHistory(LocalDateTime endinguDate, Long id) {
		return stateHitoryRepository.updateUnlockedHistory(endinguDate, id);
	}

	public Optional<StateHistory> getLastRegister() {
		return stateHitoryRepository.getLastRegister();
	}

}
