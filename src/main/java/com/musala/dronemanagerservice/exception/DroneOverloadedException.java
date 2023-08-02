package com.musala.dronemanagerservice.exception;

import com.musala.dronemanagerservice.model.entiry.Drone;

public class DroneOverloadedException extends RuntimeException {

  private static final String ERROR_MESSAGE_TEMPLATE = "Drone %s has %s g weight limit. The current weight is: %s g";

  public DroneOverloadedException(Drone drone, Integer loadedWeight) {
    super(String.format(ERROR_MESSAGE_TEMPLATE, drone.getSerialNumber(), drone.getWeightLimit(),
        loadedWeight));
  }
}
