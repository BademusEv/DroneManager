package com.musala.dronemanagerservice.validator.impl;

import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import com.musala.dronemanagerservice.validator.Validation;
import org.springframework.stereotype.Component;

@Component
public class MaxLoadedWeightValidation implements Validation<Drone> {

  private static final String ERROR_MESSAGE_TEMPLATE = "Drone %s has %s g weight limit. The current weight is: %s g";

  @Override
  public String validate(Drone entity) {
    int loaded = entity.getMedications().stream().mapToInt(Medication::getWeight).sum();
    String validationError = null;
    if (loaded > entity.getWeightLimit()) {
      validationError = String.format(ERROR_MESSAGE_TEMPLATE,
          entity.getSerialNumber(), entity.getWeightLimit(), loaded);
    }
    return validationError;
  }

  @Override
  public Class<Drone> getType() {
    return Drone.class;
  }
}
