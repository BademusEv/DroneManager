package com.musala.dronemanagerservice.validator;

public interface Validation<T> {

  String validate(T entity);

  Class<T> getType();
}
