package com.musala.dronemanagerservice.client;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DroneBatteryCheckClient {
    private final RestTemplate restTemplate;

    public BatteryDto checkBattery(String serialNumber){
        return restTemplate.getForObject("http://localhost:8080/mock/drone/battery/" + serialNumber, BatteryDto.class);
    }
}
