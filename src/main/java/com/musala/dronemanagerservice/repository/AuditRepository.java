package com.musala.dronemanagerservice.repository;

import com.musala.dronemanagerservice.model.entiry.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<Audit, String> {

}
