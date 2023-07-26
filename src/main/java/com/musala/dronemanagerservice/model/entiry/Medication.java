package com.musala.dronemanagerservice.model.entiry;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class Medication {
    @Pattern(regexp = "^[A-Z0-9-_]+$", message = "allowed only upper case letters, underscore and numbers")
    private String name;
    private Integer weight;
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "allowed only upper case letters, underscore and numbers")
    private String code;
    private String image;
}
