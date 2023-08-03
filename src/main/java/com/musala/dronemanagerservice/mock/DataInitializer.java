package com.musala.dronemanagerservice.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.dronemanagerservice.model.entiry.Drone;
import com.musala.dronemanagerservice.repository.DroneRepository;
import java.net.URL;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("demo")
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

  private static final String DB_INIT_PATH = "scripts/data-init.json";
  private final DroneRepository droneRepository;
  private final ObjectMapper objectMapper;

  @Override
  @SneakyThrows
  public void run(ApplicationArguments args) {
    if (droneRepository.count() == 0) {
      log.info("init data in db");
      URL initDataUrl = this.getClass().getClassLoader().getResource(DB_INIT_PATH);
      List<Drone> drones = objectMapper.readValue(initDataUrl, new TypeReference<>() {
      });
      droneRepository.saveAll(drones);
    }
  }
}
