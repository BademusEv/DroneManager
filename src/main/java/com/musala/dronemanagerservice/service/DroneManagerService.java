package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;

import java.util.Set;

public interface DroneManagerService {
    void registerDrone(RegisterDroneDto drone);

    DroneDto loadDrone(String serialNumber, Set<MedicationDto> medication);

    Set<MedicationDto> checkLoading(String serialNumber);

    Set<DroneDto> getAvailableDrones();

    BatteryDto checkBattery(String serialNumber);
}
