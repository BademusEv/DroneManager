package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DroneManagerServiceImpl implements DroneManagerService {
    @Override
    public void registerDrone(RegisterDroneDto drone) {

        throw new RuntimeException("not implemented yet");
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
