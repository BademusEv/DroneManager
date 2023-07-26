package com.musala.dronemanagerservice.model.dto;

import com.musala.dronemanagerservice.model.constant.Model;

import javax.validation.constraints.Size;

public record RegisterDroneDto(
        @Size(max = 100, message = "Serial max size is 100") String serialNumber,
        Model model,
        Integer weightLimit
) {
}
