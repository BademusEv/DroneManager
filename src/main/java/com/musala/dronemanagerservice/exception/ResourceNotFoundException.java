package com.musala.dronemanagerservice.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE_TEMPLATE = "%s with %s %s doesn't exist";

  public ResourceNotFoundException(String resourceName, String idName, String idValue) {
    super(String.format(ERROR_MESSAGE_TEMPLATE, resourceName, idName, idValue));
  }
}
