package com.musala.dronemanagerservice.mapper;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import java.util.Set;

public interface DroneMapper {

  Drone mapToEntity(RegisterDroneDto dto);

  DroneDto mapToDto(Drone entity);

  Set<DroneDto> mapToDtoSet(Set<Drone> drones);

  BatteryDto mapToBatteryDto(Drone drone);
}
