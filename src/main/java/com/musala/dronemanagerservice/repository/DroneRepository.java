package com.musala.dronemanagerservice.repository;

import com.musala.dronemanagerservice.model.entiry.Drone;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DroneRepository extends MongoRepository<Drone, String> {

}
