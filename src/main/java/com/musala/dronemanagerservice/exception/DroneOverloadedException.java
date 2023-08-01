package com.musala.dronemanagerservice.exception;

import com.musala.dronemanagerservice.model.entiry.Drone;

public class DroneOverloadedException extends RuntimeException {

  public DroneOverloadedException(Drone drone, Integer loadedWeight) {
    super(String.format("Drone %s has %s g weight limit. The current weight is: %s g",
        drone.getSerialNumber(), drone.getWeightLimit(), loadedWeight));
  }
}
