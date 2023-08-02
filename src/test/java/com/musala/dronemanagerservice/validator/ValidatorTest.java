package com.musala.dronemanagerservice.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.musala.dronemanagerservice.model.entiry.Drone;
import jakarta.validation.ValidationException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ValidatorTest {

  @Mock
  ValidationFactory factory;
  @InjectMocks
  Validator validator;

  @Test
  @SuppressWarnings("unchecked")
  void testValidation() {
    Validation<Drone> validation1 = Mockito.mock(Validation.class);
    when(validation1.validate(any())).thenReturn("validation message 1");
    Validation<Drone> validation2 = Mockito.mock(Validation.class);
    when(validation2.validate(any())).thenReturn("validation message 2");

    List<Validation<Drone>> validations = List.of(validation1, validation2);
    when(factory.getValidations(Drone.class)).thenReturn(validations);
    Drone drone = new Drone();

    ValidationException exception = assertThrows(ValidationException.class,
        () -> validator.validate(drone));

    assertEquals("validation message 1; validation message 2", exception.getMessage());
    validations.forEach(validation -> verify(validation).validate(drone));

  }
}