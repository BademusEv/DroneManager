package com.musala.dronemanagerservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.musala.dronemanagerservice.Utils;
import com.musala.dronemanagerservice.mapper.implementation.DroneMapperImpl;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DroneMapperImplTest {

  MedicationMapper medicationMapper = Mockito.mock(MedicationMapper.class);
  DroneMapperImpl droneMapper = new DroneMapperImpl(medicationMapper);

  @Test
  void testMapToEntity() {
    RegisterDroneDto registerDroneDto = new RegisterDroneDto("qef3r124ewgdsf-351rf-3t",
        Model.MIDDLEWEIGHT, 500);

    Drone entity = droneMapper.mapToEntity(registerDroneDto);

    assertEquals(entity.getSerialNumber(), registerDroneDto.serialNumber());
    assertEquals(entity.getModel(), registerDroneDto.model());
    assertEquals(entity.getWeightLimit(), registerDroneDto.weightLimit());
    assertEquals(entity.getState(), State.IDLE);
    assertEquals((byte) 0, entity.getBatteryCapacity());
    assertNotNull(entity.getMedications());
    assertTrue(entity.getMedications().isEmpty());
  }

  @Test
  void testMapToSet() {
    Set<Drone> generatedOriginalSet = IntStream.range(0, 4)
        .mapToObj(i -> Utils.getStockDrone())
        .collect(Collectors.toSet());
    when(medicationMapper.toDtoSet(eq(Set.of()))).thenReturn(Set.of());

    Set<DroneDto> mappedDrones = droneMapper.mapToDtoSet(generatedOriginalSet);

    Set<String> originalSerialNumbers = mappedDrones.stream()
        .map(DroneDto::serialNumber)
        .collect(Collectors.toSet());
    Set<String> mappedDronesSerialNumbers = mappedDrones.stream()
        .map(DroneDto::serialNumber)
        .collect(Collectors.toSet());

    assertEquals(originalSerialNumbers, mappedDronesSerialNumbers);
    verify(medicationMapper, times(4)).toDtoSet(Set.of());
  }

  @Test
  void testMapToBatteryDto() {
    Drone drone = Utils.getStockDrone();
    BatteryDto batteryDto = droneMapper.mapToBatteryDto(drone);
    assertEquals(drone.getSerialNumber(), batteryDto.serialNumber());
    assertEquals(drone.getBatteryCapacity(), batteryDto.batteryCapacity());
  }
}