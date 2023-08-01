package com.musala.dronemanagerservice.mapper.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.entiry.Medication;
import java.util.stream.Collectors;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

class MedicationMapperImplTest {

    MedicationMapper medicationMapper = new MedicationMapperImpl();
    EasyRandom easyRandom = new EasyRandom();

    @Test
    void testSingleObjectMapping() {
        var originalMedication = easyRandom.nextObject(Medication.class);
        var medicationDto = medicationMapper.toDto(originalMedication);
        var actualMedication = medicationMapper.toEntity(medicationDto);
        assertEquals(originalMedication, actualMedication);
    }

    @Test
    void testSetMapping() {
        var originalMedications = easyRandom.objects(Medication.class, 4)
                .collect(Collectors.toSet());
        var dtoSet = medicationMapper.toDtoSet(originalMedications);
        var actualMedications = medicationMapper.toEntitySet(dtoSet);
        assertEquals(originalMedications, actualMedications);
    }
}