package com.musala.dronemanagerservice.model.entiry;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit")
public record Audit(@Id String id, String serialNumber, Byte batteryCapacity, LocalDateTime time) {
}
