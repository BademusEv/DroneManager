package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.client.DroneBatteryCheckClient;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneManagerServiceImpl implements DroneManagerService {

  private final DroneMapper droneMapper;
  private final MedicationMapper medicationMapper;
  private final DroneRepository repository;
  private final Validator validator;
  private final DroneBatteryCheckClient batteryCheckClient;

  @Value("${validation.drone.minimum-battery}")
  private Byte minimumBattery;

  @Override
  public void registerDrone(RegisterDroneDto droneDto) {
    Drone drone = droneMapper.mapToEntity(droneDto);
    boolean isPresent = repository.findById(drone.getSerialNumber()).isPresent();
    if (isPresent) {
      throw new EntityAlreadyExistException("Drone", droneDto.serialNumber());
    }
    BatteryDto battery = batteryCheckClient.checkBattery(drone.getSerialNumber());
    drone.setBatteryCapacity(battery.batteryCapacity());
    repository.save(drone);
  }

  @Override
  public DroneDto loadDrone(String serialNumber, Set<MedicationDto> medicationDtos) {
    Drone drone = getDrone(serialNumber);

    Set<Medication> medications = medicationMapper.toEntitySet(medicationDtos);
    drone.getMedications().addAll(medications);
    validator.validate(drone);
    drone.setState(State.LOADING);
    drone = repository.save(drone);
    return droneMapper.mapToDto(drone);
  }

  @Override
  public Set<MedicationDto> checkLoading(String serialNumber) {
    Drone drone = getDrone(serialNumber);
    return medicationMapper.toDtoSet(drone.getMedications());
  }

  @Override
  public Set<DroneDto> getAvailableDrones() {
    Set<Drone> availableDrones = repository.findAllByStateInAndBatteryCapacityAfter(
        Set.of(State.IDLE, State.LOADING), (byte) (minimumBattery - 1));
    return droneMapper.mapToDtoSet(availableDrones);
  }

  @Override
  public BatteryDto checkBattery(String serialNumber) {
    Drone drone = getDrone(serialNumber);
    return droneMapper.mapToBatteryDto(drone);
  }

  private Drone getDrone(String serialNumber) {
    return repository.findById(serialNumber)
        .orElseThrow(() -> new DroneNotFoundException(serialNumber));
  }
}
