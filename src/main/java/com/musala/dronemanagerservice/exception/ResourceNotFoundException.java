package com.musala.dronemanagerservice.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resourceName, String idName, String idValue) {
    super(String.format("%s with %s: '%s' doesn't exist", resourceName, idName, idValue));
  }
}
