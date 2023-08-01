package com.musala.dronemanagerservice.exception;

public class EntityAlreadyExistException extends RuntimeException {

  public EntityAlreadyExistException(String name, String id) {
    super(String.format("Entity %s with id %s already exist", name, id));
  }
}
