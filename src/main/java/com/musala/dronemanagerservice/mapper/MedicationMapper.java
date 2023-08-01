package com.musala.dronemanagerservice.mapper;

import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.entiry.Medication;
import java.util.Set;

public interface MedicationMapper {

  Medication toEntity(MedicationDto dto);

  Set<Medication> toEntitySet(Set<MedicationDto> dtos);

  Set<MedicationDto> toDtoSet(Set<Medication> medications);

  MedicationDto toDto(Medication medication);
}
