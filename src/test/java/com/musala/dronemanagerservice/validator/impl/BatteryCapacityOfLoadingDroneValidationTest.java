package com.musala.dronemanagerservice.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.musala.dronemanagerservice.Utils;
import com.musala.dronemanagerservice.model.entiry.Drone;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class BatteryCapacityOfLoadingDroneValidationTest {

  BatteryCapacityOfLoadingDroneValidation validation = new BatteryCapacityOfLoadingDroneValidation();

  @Test
  void testChargedDroneLoading() {
    Drone drone = Utils.getStockDrone();
    drone.setBatteryCapacity((byte) 25);
    String errorMessage = validation.validate(drone);
    assertTrue(StringUtils.isEmpty(errorMessage));
  }

  @Test
  void testDischargedDroneLoading() {
    Drone drone = Utils.getStockDrone();
    drone.setBatteryCapacity((byte) 24);
    String errorMessage = validation.validate(drone);
    assertEquals(String.format("Drone %s has 24%% battery capacity and need to be charged",
        drone.getSerialNumber()), errorMessage);
  }
}