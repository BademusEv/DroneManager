package com.musala.dronemanagerservice.mapper;

import com.musala.dronemanagerservice.mapper.implementation.DroneMapperImpl;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class DroneMapperImplTest {
    @Mock
    MedicationMapper medicationMapper;
    DroneMapperImpl droneMapper = new DroneMapperImpl(medicationMapper);

    @Test
    void mapToEntity() {
        RegisterDroneDto registerDroneDto = new RegisterDroneDto("qef3r124ewgdsf-351rf-3t",
                Model.MIDDLEWEIGHT, 500);

        Drone entity = droneMapper.mapToEntity(registerDroneDto);

        assertEquals(entity.getSerialNumber(), registerDroneDto.serialNumber());
        assertEquals(entity.getModel(), registerDroneDto.model());
        assertEquals(entity.getWeightLimit(), registerDroneDto.weightLimit());
        assertEquals(entity.getState(), State.IDLE);
        assertNull(entity.getBatteryCapacity());
        assertNotNull(entity.getMedications());
        assertTrue(entity.getMedications().isEmpty());
    }
}