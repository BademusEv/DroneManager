package com.musala.dronemanagerservice.model.entiry;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audit")
public record Audit(@Id String id, String serialNumber, Byte batteryCapacity, LocalDateTime time) {

}
