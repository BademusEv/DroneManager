package com.musala.dronemanagerservice.mapper.implementation;

import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.entiry.Medication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicationMapperImplTest {
    MedicationMapper medicationMapper = new MedicationMapperImpl();
    EasyRandom easyRandom = new EasyRandom();

    @Test
    void testSingleObjectMapping() {
        var originalMedication = easyRandom.nextObject(Medication.class);
        var medicationDto = medicationMapper.toDto(originalMedication);
        var actualMedication  = medicationMapper.toEntity(medicationDto);
        assertEquals(originalMedication, actualMedication);
    }

    @Test
    void testSetMapping() {
        var originalMedications = easyRandom.objects(Medication.class, 4).collect(Collectors.toSet());
        var dtoSet = medicationMapper.toDtoSet(originalMedications);
        var actualMedications = medicationMapper.toEntitySet(dtoSet);
        assertEquals(originalMedications, actualMedications);
    }
}