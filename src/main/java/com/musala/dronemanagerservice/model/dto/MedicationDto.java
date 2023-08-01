package com.musala.dronemanagerservice.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicationDto(
    @Pattern(regexp = "^[A-Za-z\\d-_]+$", message = "Name must contain only letters, numbers, ‘-‘, ‘_’")
    @NotNull(message = "Name must be filled")
    String name,
    @NotNull(message = "Weight must be filled")
    Integer weight,
    @NotNull(message = "Code must be filled")
    @Pattern(regexp = "^[A-Z\\d_]+$", message = "Code must contain only upper case letters, underscore and numbers")
    String code,
    String image
) {

}
