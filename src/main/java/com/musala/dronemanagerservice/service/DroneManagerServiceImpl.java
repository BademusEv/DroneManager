package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.exception.DroneNotFoundException;
import com.musala.dronemanagerservice.exception.EntityAlreadyExistException;
import com.musala.dronemanagerservice.mapper.DroneMapper;
import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import com.musala.dronemanagerservice.repository.DroneRepository;
import com.musala.dronemanagerservice.validator.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneManagerServiceImpl implements DroneManagerService {

  private final DroneMapper droneMapper;
  private final MedicationMapper medicationMapper;
  private final DroneRepository repository;
  private final Validator validator;

  @Override
  public void registerDrone(RegisterDroneDto droneDto) {
    Drone drone = droneMapper.mapToEntity(droneDto);
    boolean isPresent = repository.findById(drone.getSerialNumber()).isPresent();
    if (isPresent) {
      throw new EntityAlreadyExistException("Drone", droneDto.serialNumber());
    }
    repository.save(drone);
  }

  @Override
  public DroneDto loadDrone(String serialNumber, Set<MedicationDto> medicationDtos) {
    Drone drone = repository.findById(serialNumber)
        .orElseThrow(() -> new DroneNotFoundException(serialNumber));

    Set<Medication> medications = medicationMapper.toEntitySet(medicationDtos);
    drone.getMedications().addAll(medications);
    validator.validate(drone);

    drone = repository.save(drone);
    return droneMapper.mapToDto(drone);
  }

  @Override
  public Set<MedicationDto> checkLoading(String serialNumber) {
    Drone drone = repository.findById(serialNumber)
        .orElseThrow(() -> new DroneNotFoundException(serialNumber));
    return medicationMapper.toDtoSet(drone.getMedications());
  }

  @Override
  public Set<DroneDto> getAvailableDrones() {
    Set<Drone> availableDrones = repository.findAllByStateIn(Set.of(State.IDLE, State.LOADING));
    return droneMapper.mapToDtoSet(availableDrones);
  }

  @Override
  public BatteryDto checkBattery(String serialNumber) {
    Drone drone = repository.findById(serialNumber)
        .orElseThrow(() -> new DroneNotFoundException(serialNumber));
    return droneMapper.mapToBatteryDto(drone);
  }
}
