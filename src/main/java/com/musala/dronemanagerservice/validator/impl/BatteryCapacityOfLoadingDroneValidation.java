package com.musala.dronemanagerservice.validator.impl;

import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.validator.Validation;
import org.springframework.stereotype.Component;

@Component
public class BatteryCapacityOfLoadingDroneValidation implements Validation<Drone> {

  private static final String ERROR_MESSAGE_TEMPLATE = "Drone %s has %s%% battery capacity and need to be charged";

  @Override
  public String validate(Drone drone) {
    String errorMessage = null;
    if (drone.getBatteryCapacity() < 25) {
      errorMessage = String.format(ERROR_MESSAGE_TEMPLATE,
          drone.getSerialNumber(), drone.getBatteryCapacity());
    }
    return errorMessage;
  }

  @Override
  public Class<Drone> getType() {
    return Drone.class;
  }
}
