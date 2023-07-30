package com.musala.dronemanagerservice.model.entiry;

import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "drone")
public class Drone {
    @Id
    @Size(max = 100, message = "Serial max size is 100")
    private String serialNumber;
    private Model model;
    private Integer weightLimit;
    @Max(value = 100, message = "Battery capacity should be between 0 and 100")
    @Min(value = 0, message = "Battery capacity should be between 0 and 100")
    private Byte batteryCapacity;
    @Indexed(name = "stateIndex")
    private State state;
    private Set<Medication> medications = new HashSet<>();

    public Drone(String serialNumber, Model model, Integer weightLimit, Byte batteryCapacity, State state, Set<Medication> medications) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.medications = Optional.ofNullable(medications).orElse(new HashSet<>());
    }
}
