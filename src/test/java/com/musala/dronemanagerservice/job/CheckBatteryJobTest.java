package com.musala.dronemanagerservice.job;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.musala.dronemanagerservice.client.DroneBatteryCheckClient;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.repository.AuditRepository;
import com.musala.dronemanagerservice.repository.DroneRepository;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CheckBatteryJobTest {

  @Mock
  DroneRepository droneRepository;
  @Mock
  DroneBatteryCheckClient checkClient;
  @Mock
  AuditRepository auditRepository;

  @InjectMocks
  CheckBatteryJob job;

  @Test
  void testCheckBatteryJobUpdatesAllDronesBatteryAndLogAudit() {
    EasyRandom easyRandom = new EasyRandom();
    List<Drone> drones = easyRandom.objects(Drone.class, 4).toList();

    drones.forEach(drone -> when(checkClient.checkBattery(drone.getSerialNumber())).thenReturn(
        new BatteryDto(drone.getSerialNumber(), (byte) (drone.getBatteryCapacity() + 5))));
    when(droneRepository.findAll()).thenReturn(drones);

    job.checkBattery();

    drones.forEach(drone -> {
      verify(checkClient).checkBattery(drone.getSerialNumber());
      verify(droneRepository).save(drone);
      verify(auditRepository, times(4)).save(any());
    });
  }
}