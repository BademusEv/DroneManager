package com.musala.dronemanagerservice.model.dto;

import com.musala.dronemanagerservice.model.constant.Model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDroneDto(
        @Size(max = 100, message = "Serial must be less than 100")
        @NotNull(message = "Serial number must be filled")
        String serialNumber,
        @NotNull(message = "Model must be filled")
        Model model,
        Integer weightLimit
) {
}
