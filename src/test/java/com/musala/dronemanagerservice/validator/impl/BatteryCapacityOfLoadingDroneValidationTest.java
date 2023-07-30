package com.musala.dronemanagerservice.validator.impl;

import com.musala.dronemanagerservice.Utils;
import com.musala.dronemanagerservice.model.entiry.Drone;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatteryCapacityOfLoadingDroneValidationTest {

    BatteryCapacityOfLoadingDroneValidation validation = new BatteryCapacityOfLoadingDroneValidation();

    @Test
    void testChargedDroneLoading() {
        Drone drone = Utils.getStockDrone();
        drone.setBatteryCapacity(25.0f);
        String errorMessage = validation.validate(drone);
        assertTrue(StringUtils.isEmpty(errorMessage));
    }

    @Test
    void testDischargedDroneLoading() {
        Drone drone = Utils.getStockDrone();
        drone.setBatteryCapacity(24.99f);
        String errorMessage = validation.validate(drone);
        assertEquals(String.format("Drone %s has 24.99 battery capacity and need to be charged", drone.getSerialNumber()), errorMessage);
    }
}