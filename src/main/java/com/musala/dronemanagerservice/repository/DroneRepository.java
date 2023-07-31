package com.musala.dronemanagerservice.repository;

import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.entiry.Drone;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface DroneRepository extends MongoRepository<Drone, String> {
    Set<Drone> findAllByStateIn(Set<State> state);
}
