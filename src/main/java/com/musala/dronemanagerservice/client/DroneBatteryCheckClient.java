package com.musala.dronemanagerservice.client;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DroneBatteryCheckClient {

  private final RestTemplate restTemplate;
  @Value("${client.drone.check-battery.uri}")
  private String droneBatteryCheckUri;

  public BatteryDto checkBattery(String serialNumber) {
    return restTemplate.getForObject(droneBatteryCheckUri + serialNumber, BatteryDto.class);
  }
}
