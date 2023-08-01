package com.musala.dronemanagerservice.job;

import com.musala.dronemanagerservice.client.DroneBatteryCheckClient;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.entiry.Audit;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.repository.AuditRepository;
import com.musala.dronemanagerservice.repository.DroneRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckBatteryJob {

  private final DroneRepository droneRepository;
  private final DroneBatteryCheckClient checkClient;
  private final AuditRepository auditRepository;

  @Scheduled(fixedRate = 60000)
  public void checkBattery() {
    log.info("update drone's battery info");
    List<Drone> drones = droneRepository.findAll();
    drones.forEach(drone -> {
      BatteryDto battery = checkClient.checkBattery(drone.getSerialNumber());
      drone.setBatteryCapacity(battery.batteryCapacity());
      droneRepository.save(drone);
      Audit audit = new Audit(UUID.randomUUID().toString(), drone.getSerialNumber(),
          battery.batteryCapacity(), LocalDateTime.now());
      auditRepository.save(audit);
    });
    log.info("drone's battery info updated");
  }
}
