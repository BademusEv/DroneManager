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
import com.musala.dronemanagerservice.validator.Validator;
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
    public DroneDto loadDrone(String serialNumber, Set<MedicationDto> medicationDtos) {
        Drone drone = repository.findById(serialNumber).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format("Drone with serial number: '%s' doesn't exist", serialNumber)));

        Set<Medication> medications = medicationMapper.toEntitySet(medicationDtos);
        drone.getMedications().addAll(medications);
        Validator.validate(drone);

        drone = repository.save(drone);
        return droneMapper.mapToDto(drone);
    }

    @Override
    public Set<MedicationDto> checkLoading(String serialNumber) {
        Drone drone = repository.findById(serialNumber).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format("Drone with serial number: '%s' doesn't exist", serialNumber)));

        return medicationMapper.toDtoSet(drone.getMedications());
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
