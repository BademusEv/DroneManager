package com.musala.dronemanagerservice.model.dto;

public record MedicationDto(
        String name,
        Integer weight,
        String code,
        String image
) {
}
