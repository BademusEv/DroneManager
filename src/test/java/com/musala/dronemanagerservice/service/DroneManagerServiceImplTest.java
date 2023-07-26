package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.mapper.implementation.DroneMapperImpl;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DroneManagerServiceImplTest {

    @Mock
    DroneMapperImpl mapper;
    @Mock
    DroneRepository repository;
    @InjectMocks
    DroneManagerServiceImpl service;

    @Test
    void testRegisterDrone() {
        String serialNumber = "fwoife_w23tgjwio_2342_efwk";
        RegisterDroneDto dto = new RegisterDroneDto(serialNumber, Model.LIGHTWEIGHT, 500);
        Drone drone = Drone.builder()
                .serialNumber(serialNumber)
                .model(Model.LIGHTWEIGHT)
                .weightLimit(500)
                .state(State.IDLE)
                .build();

        when(mapper.mapToEntity(dto)).thenReturn(drone);
        service.registerDrone(dto);
        verify(mapper).mapToEntity(dto);
        verify(repository).save(drone);
    }

    @Test
    void loadDrone() {
    }

    @Test
    void checkLoading() {
    }

    @Test
    void getAvailableDrones() {
    }

    @Test
    void checkBattery() {
    }
}