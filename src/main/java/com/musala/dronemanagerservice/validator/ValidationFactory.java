package com.musala.dronemanagerservice.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ValidationFactory {

  private final Map<Class<?>, List<Validation<?>>> validationMap;

  public ValidationFactory(List<Validation<?>> validations) {
    this.validationMap = validations.stream().collect(Collectors.groupingBy(Validation::getType));
  }

  @SuppressWarnings("unchecked")
  public <T> List<Validation<T>> getValidations(Class<T> clazz) {
    return validationMap.get(clazz).stream().map(validation -> (Validation<T>) validation).toList();
  }
}
