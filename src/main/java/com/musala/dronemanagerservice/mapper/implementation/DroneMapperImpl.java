package com.musala.dronemanagerservice.mapper.implementation;

import com.musala.dronemanagerservice.mapper.DroneMapper;
import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DroneMapperImpl implements DroneMapper {

    private final MedicationMapper medicationMapper;

    public Drone mapToEntity(RegisterDroneDto dto){
        return Drone.builder()
                .serialNumber(dto.serialNumber())
                .weightLimit(dto.weightLimit())
                .model(dto.model())
                .state(State.IDLE)
                .build();
    }

    @Override
    public DroneDto mapToDto(Drone entity) {
        return new DroneDto(
                entity.getSerialNumber(),
                entity.getModel(),
                entity.getWeightLimit(),
                entity.getBatteryCapacity(),
                entity.getState(),
                medicationMapper.toDtoSet(entity.getMedications())
        );
    }


}
