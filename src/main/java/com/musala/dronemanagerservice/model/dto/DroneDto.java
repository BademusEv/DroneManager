package com.musala.dronemanagerservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DroneDto(
    String serialNumber,
    Model model,
    Integer weightLimit,
    Byte batteryCapacity,
    State state,
    Set<MedicationDto> medications
) {

}
