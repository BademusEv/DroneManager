package com.musala.dronemanagerservice.mapper.implementation;

import com.musala.dronemanagerservice.mapper.MedicationMapper;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.entiry.Medication;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapperImpl implements MedicationMapper {

    @Override
    public Medication toEntity(MedicationDto dto) {
        return Medication.builder()
                .name(dto.name())
                .code(dto.code())
                .weight(dto.weight())
                .image(dto.image())
                .build();
    }

    @Override
    public Set<Medication> toEntitySet(Set<MedicationDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<MedicationDto> toDtoSet(Set<Medication> medications) {
        return medications.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Override
    public MedicationDto toDto(Medication medication) {
        return new MedicationDto(medication.getName(), medication.getWeight(), medication.getCode(),
                medication.getImage());
    }

}
