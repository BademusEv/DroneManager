package com.musala.dronemanagerservice.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.musala.dronemanagerservice.Utils;
import com.musala.dronemanagerservice.exception.EntityAlreadyExistException;
import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.mapper.implementation.DroneMapperImpl;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import com.musala.dronemanagerservice.repository.DroneRepository;
import com.musala.dronemanagerservice.validator.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DroneManagerServiceImplTest {

  @Mock
  DroneMapperImpl droneMapper;
  @Mock
  MedicationMapper medicationMapper;
  @Mock
  DroneRepository repository;
  @Mock
  Validator validator;
  @InjectMocks
  DroneManagerServiceImpl service;

  @Test
  void testRegisterDrone() {
    var drone = Utils.getStockDrone();
    var dto = new RegisterDroneDto(drone.getSerialNumber(), Model.LIGHTWEIGHT, 500);

    when(droneMapper.mapToEntity(dto)).thenReturn(drone);
    service.registerDrone(dto);
    verify(droneMapper).mapToEntity(dto);
    verify(repository).save(drone);
  }

  @Test
  void testRegisterExistingDroneThrowsException() {
    var drone = Utils.getStockDrone();
    var dto = new RegisterDroneDto(drone.getSerialNumber(), Model.LIGHTWEIGHT, 500);
    when(droneMapper.mapToEntity(dto)).thenReturn(drone);
    when(repository.findById(drone.getSerialNumber())).thenReturn(Optional.of(drone));

    assertThrows(EntityAlreadyExistException.class, () -> service.registerDrone(dto));
    verify(droneMapper).mapToEntity(dto);

    verify(repository, never()).save(drone);
  }

  @Test
  void testDroneLoading() {
    var medicationsDto = Set.of(new MedicationDto("A", 41, "13431", "base64EncodedImage1"));
    var medications = Set.of(
        Medication.builder().name("A").weight(41).code("13431").image("base64EncodedImage1")
            .build());

    var drone = Utils.getStockDrone();
    when(repository.findById(eq("egwfe134af"))).thenReturn(Optional.of(drone));
    when(medicationMapper.toEntitySet(eq(medicationsDto))).thenReturn(medications);
    when(repository.save(drone)).thenReturn(drone);
    doNothing().when(validator).validate(drone);
    var expectedDrone = new DroneDto("egwfe134af", Model.LIGHTWEIGHT, 500, (byte) 50, State.LOADED,
        medicationsDto);
    when(droneMapper.mapToDto(drone)).thenReturn(expectedDrone);

    var actualDrone = service.loadDrone("egwfe134af", medicationsDto);

    assertEquals(expectedDrone, actualDrone);
    verify(repository).findById("egwfe134af");
    verify(medicationMapper).toEntitySet(medicationsDto);
    verify(validator).validate(drone);
    verify(repository).save(drone);
    verify(droneMapper).mapToDto(drone);

  }

  @Test
  void testCheckLoading() {
    var medications = Set.of(
        Medication.builder().name("A").code("1234").weight(34).image("base64Image1").build(),
        Medication.builder().name("B").code("3542").weight(46).image("base64Image2").build()
    );
    var medicationsDto = Set.of(
        new MedicationDto("A", 34, "1234", "base64Image1"),
        new MedicationDto("B", 46, "3542", "base64Image2")
    );
    Drone drone = Drone.builder()
        .serialNumber("foqwrm2143")
        .medications(medications)
        .build();

    when(repository.findById("foqwrm2143")).thenReturn(Optional.of(drone));
    when(medicationMapper.toDtoSet(medications)).thenReturn(medicationsDto);

    Set<MedicationDto> actualMedications = service.checkLoading("foqwrm2143");

    assertEquals(medicationsDto, actualMedications);
    verify(repository).findById("foqwrm2143");
    verify(medicationMapper).toDtoSet(medications);
  }

  @Test
  void testGetAvailableDrones() {
    Set<Drone> drones = Utils.getStockDrones(4);
    Set<String> serialNumbers = drones.stream().map(Drone::getSerialNumber)
        .collect(Collectors.toSet());
    Set<DroneDto> droneDtos = Utils.getStockDroneDto(serialNumbers);

    when(repository.findAllByStateIn(Set.of(State.IDLE, State.LOADING))).thenReturn(drones);
    when(droneMapper.mapToDtoSet(drones)).thenReturn(droneDtos);

    Set<DroneDto> availableDrones = service.getAvailableDrones();

    Set<String> actualSerialNumbers = availableDrones.stream()
        .map(DroneDto::serialNumber)
        .collect(Collectors.toSet());

    assertEquals(serialNumbers, actualSerialNumbers);
    verify(repository).findAllByStateIn(Set.of(State.IDLE, State.LOADING));
    verify(droneMapper).mapToDtoSet(drones);
  }

  @Test
  void testCheckBattery() {
    String serialNumber = "adegwkfe314r2";
    Drone drone = Utils.getStockDrone();
    BatteryDto expectedBattery = new BatteryDto(serialNumber, drone.getBatteryCapacity());

    when(repository.findById(serialNumber)).thenReturn(Optional.of(drone));
    when(droneMapper.mapToBatteryDto(drone))
        .thenReturn(expectedBattery);

    BatteryDto actualBattery = service.checkBattery(serialNumber);

    assertNotNull(actualBattery);
    assertEquals(expectedBattery, actualBattery);
    verify(repository).findById(serialNumber);
    verify(droneMapper).mapToBatteryDto(drone);
  }
}