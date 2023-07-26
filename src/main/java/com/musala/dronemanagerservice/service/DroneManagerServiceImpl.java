package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.mapper.DroneMapper;
import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import com.musala.dronemanagerservice.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DroneManagerServiceImpl implements DroneManagerService {

    private final DroneMapper droneMapper;
    private final MedicationMapper medicationMapper;
    private final DroneRepository repository;

    @Override
    public void registerDrone(RegisterDroneDto droneDto) {
        Drone drone = droneMapper.mapToEntity(droneDto);
        repository.save(drone);
    }

    @Override
    public void loadDrone(String serialNumber, MedicationDto medication) {
        throw new RuntimeException("not implemented yet");

    }

    @Override
    public Set<MedicationDto> checkLoading(String serialNumber) {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Set<DroneDto> getAvailableDrones() {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public BatteryDto checkBattery(String serialNumber) {
        throw new RuntimeException("not implemented yet");
    }
}
