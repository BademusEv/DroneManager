package com.musala.dronemanagerservice.mapper;

import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;

public interface DroneMapper {

    Drone mapToEntity(RegisterDroneDto dto);
    DroneDto mapToDto(Drone entity);
}
