package com.musala.dronemanagerservice.model.entiry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medication {

  private String name;
  private Integer weight;
  private String code;
  private String image;
}
