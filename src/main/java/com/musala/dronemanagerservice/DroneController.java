package com.musala.dronemanagerservice;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.service.DroneManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void loadDrone(@PathVariable String serialNumber, MedicationDto medication) {
        service.loadDrone(serialNumber, medication);
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
