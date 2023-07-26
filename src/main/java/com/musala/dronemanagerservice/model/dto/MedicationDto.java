package com.musala.dronemanagerservice.model.dto;

import java.util.Base64;

public record MedicationDto(
        String name,
        Integer weight,
        String code,
        String image
) {
}
