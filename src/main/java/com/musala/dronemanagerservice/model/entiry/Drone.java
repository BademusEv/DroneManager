package com.musala.dronemanagerservice.model.entiry;

import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "drone")
public class Drone {

  @Id
  private String serialNumber;
  private Model model;
  private Integer weightLimit;
  private Byte batteryCapacity;
  @Indexed(name = "stateIndex")
  private State state;
  private Set<Medication> medications = new HashSet<>();

  public Drone(String serialNumber, Model model, Integer weightLimit, Byte batteryCapacity,
      State state, Set<Medication> medications) {
    this.serialNumber = serialNumber;
    this.model = model;
    this.weightLimit = weightLimit;
    this.batteryCapacity = batteryCapacity;
    this.state = state;
    this.medications = Optional.ofNullable(medications).orElse(new HashSet<>());
  }
}
