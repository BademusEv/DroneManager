package com.musala.dronemanagerservice.validator;

import com.musala.dronemanagerservice.exception.DroneOverloadedException;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.model.entiry.Medication;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {
    

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
        assertDoesNotThrow(() -> Validator.validate(drone));
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
        DroneOverloadedException droneOverloadedException = assertThrows(DroneOverloadedException.class, () -> Validator.validate(drone));
        assertEquals("Drone efgwbe2331advjkln has 500 g weight limit. The current weight is: 501 g", droneOverloadedException.getLocalizedMessage());
    }
}