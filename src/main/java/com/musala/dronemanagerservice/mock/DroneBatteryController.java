package com.musala.dronemanagerservice.mock;

import com.musala.dronemanagerservice.model.dto.BatteryDto;
import java.util.random.RandomGenerator;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("demo")
@RequestMapping("mock/drone/battery")
public class DroneBatteryController {

  @GetMapping("/{serialNumber}")
  public BatteryDto getMockBattery(@PathVariable String serialNumber) {
    byte randomBattery = (byte) (RandomGenerator.getDefault().nextInt(101));
    return new BatteryDto(serialNumber, randomBattery);
  }
}