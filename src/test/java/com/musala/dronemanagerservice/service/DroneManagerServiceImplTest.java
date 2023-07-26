package com.musala.dronemanagerservice.service;

import com.musala.dronemanagerservice.Utils;
import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.mapper.implementation.DroneMapperImpl;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import com.musala.dronemanagerservice.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DroneManagerServiceImplTest {

    @Mock
    DroneMapperImpl droneMapper;
    @Mock
    MedicationMapper medicationMapper;
    @Mock
    DroneRepository repository;
    @InjectMocks
    DroneManagerServiceImpl service;

    @Test
    void testRegisterDrone() {
        Drone drone = Utils.getStockDrone();
        RegisterDroneDto dto = new RegisterDroneDto(drone.getSerialNumber(), Model.LIGHTWEIGHT, 500);

        when(droneMapper.mapToEntity(dto)).thenReturn(drone);
        service.registerDrone(dto);
        verify(droneMapper).mapToEntity(dto);
        verify(repository).save(drone);
    }

    @Test
    void tesDroneLoading() {
        Set<MedicationDto> medicationsDto = Set.of(new MedicationDto("A", 41, "13431", "base64EncodedImage1"));
        Set<Medication> medications = Set.of(Medication.builder().name("A").weight(41).code("13431").image("base64EncodedImage1").build());

        Drone drone = Utils.getStockDrone();
        when(repository.findById(eq("egwfe134af"))).thenReturn(Optional.of(drone));
        when(medicationMapper.toEntitySet(eq(medicationsDto))).thenReturn(medications);
        when(repository.save(drone)).thenReturn(drone);
        DroneDto expectedDrone = new DroneDto("egwfe134af", Model.LIGHTWEIGHT, 500, 50.3f, State.LOADED, medicationsDto);
        when(droneMapper.mapToDto(drone)).thenReturn(expectedDrone);

        DroneDto actualDrone = service.loadDrone("egwfe134af", medicationsDto);

        assertEquals(expectedDrone, actualDrone);
        verify(repository).findById("egwfe134af");
        verify(medicationMapper).toEntitySet(medicationsDto);
        verify(repository).save(drone);
        verify(droneMapper).mapToDto(drone);

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