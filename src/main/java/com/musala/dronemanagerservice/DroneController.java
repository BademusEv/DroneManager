package com.musala.dronemanagerservice;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.service.DroneManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/drone")
@RequiredArgsConstructor
public class DroneController {
    private final DroneManagerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDrone(RegisterDroneDto drone) {
        service.registerDrone(drone);
    }

    @PutMapping("/load/{serialNumber}")
    public DroneDto loadDrone(@PathVariable String serialNumber, @RequestBody Set<MedicationDto> medication) {
        return service.loadDrone(serialNumber, medication);
    }

    @GetMapping("/checkLoading/{serialNumber}")
    public Set<MedicationDto> checkLoading(@PathVariable String serialNumber) {
        return service.checkLoading(serialNumber);
    }

    @GetMapping("/available")
    public Set<DroneDto> getAvailableDrones() {
        return service.getAvailableDrones();
    }

    @GetMapping("/battery/{serialNumber}")
    public BatteryDto checkBattery(@PathVariable String serialNumber) {
        return service.checkBattery(serialNumber);
    }

}
