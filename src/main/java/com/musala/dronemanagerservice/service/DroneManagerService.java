package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;

import java.util.Set;

public interface DroneManagerService {
    void registerDrone(RegisterDroneDto drone);

    void loadDrone(String serialNumber, MedicationDto medication);

    Set<MedicationDto> checkLoading(String serialNumber);

    Set<DroneDto> getAvailableDrones();

    BatteryDto checkBattery(String serialNumber);
}
