package com.musala.dronemanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroneManagerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(DroneManagerServiceApplication.class, args);
  }

}
