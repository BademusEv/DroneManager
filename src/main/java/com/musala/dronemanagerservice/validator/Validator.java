package com.musala.dronemanagerservice.validator;

import com.musala.dronemanagerservice.model.entiry.Drone;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

  private final ValidationFactory factory;

  public void validate(Drone drone) {
    List<Validation<Drone>> validations = factory.getValidations(Drone.class);
    String validationErrors = validations.stream().map(v -> v.validate(drone))
        .filter(StringUtils::isNoneEmpty)
        .collect(Collectors.joining("; "));
    if (StringUtils.isNoneEmpty(validationErrors)) {
      throw new ValidationException(validationErrors);
    }
  }
}
