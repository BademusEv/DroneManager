package com.musala.dronemanagerservice.validator.impl;

import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaxLoadedWeightValidationTest {
    

    @Test
    void testValidationDoesntThrowExceptionForAcceptableWeight() {
        Set<Medication> medicationsWithAcceptableWeight = Set.of(
                Medication.builder().weight(200).build(),
                Medication.builder().weight(299).build());
        Drone drone = Drone.builder()
                .serialNumber("efgwbe2331advjkln")
                .weightLimit(500)
                .medications(medicationsWithAcceptableWeight)
                .build();
        String errorMessage = new MaxLoadedWeightValidation().validate(drone);
        assertTrue(StringUtils.isEmpty(errorMessage));
    }

    @Test
    void testValidationThrowsExceptionForOverloadedDrone() {
        Set<Medication> medicationsWithAcceptableWeight = Set.of(
                Medication.builder().weight(202).build(),
                Medication.builder().weight(299).build());
        Drone drone = Drone.builder()
                .serialNumber("efgwbe2331advjkln")
                .weightLimit(500)
                .medications(medicationsWithAcceptableWeight)
                .build();
        String errorMessage = new MaxLoadedWeightValidation().validate(drone);
        assertEquals("Drone efgwbe2331advjkln has 500 g weight limit. The current weight is: 501 g", errorMessage);
    }
}