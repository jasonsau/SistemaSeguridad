package com.example.demo.workstation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationService {
    private WorkStationRepository workStationRepository;
    public WorkStationService(WorkStationRepository workStationRepository) {
        this.workStationRepository = workStationRepository;
    }

    public List<WorkStation> getAllWork() {
        return workStationRepository.findAll();
    }
}
