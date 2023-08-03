package com.musala.dronemanagerservice.repository;

import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.entiry.Drone;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DroneRepository extends MongoRepository<Drone, String> {

  Set<Drone> findAllByStateInAndBatteryCapacityAfter(Set<State> state, Byte capacity);
}
