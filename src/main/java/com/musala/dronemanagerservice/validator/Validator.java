package com.musala.dronemanagerservice.validator;

import com.musala.dronemanagerservice.exception.DroneOverloadedException;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public void validate(Drone drone) {
        int loadedWeight = drone.getMedications().stream().mapToInt(Medication::getWeight).sum();
        if (loadedWeight > drone.getWeightLimit()) {
            throw new DroneOverloadedException(drone, loadedWeight);
        }
    }
}
