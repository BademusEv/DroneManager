package com.musala.dronemanagerservice.validator.impl;

import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.validator.Validation;
import org.springframework.stereotype.Component;

@Component
public class BatteryCapacityOfLoadingDroneValidation implements Validation<Drone> {
    @Override
    public String validate(Drone drone) {
        String errorMessage = null;
        if(drone.getBatteryCapacity() < 25){
            errorMessage = String.format("Drone %s has %s%% battery capacity and need to be charged",
                    drone.getSerialNumber(), drone.getBatteryCapacity());
        }
        return errorMessage;
    }

    @Override
    public Class<Drone> getType() {
        return Drone.class;
    }
}
