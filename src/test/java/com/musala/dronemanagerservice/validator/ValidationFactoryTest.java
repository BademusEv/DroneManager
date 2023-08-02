package com.musala.dronemanagerservice.validator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ValidationFactoryTest {

  Validation<DroneDto> validation1;
  Validation<DroneDto> validation2;
  Validation<MedicationDto> validation3;
  ValidationFactory factory;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void init() {
    validation1 = Mockito.mock(Validation.class);
    validation2 = Mockito.mock(Validation.class);
    when(validation1.getType()).thenReturn(DroneDto.class);
    when(validation2.getType()).thenReturn(DroneDto.class);
    validation3 = Mockito.mock(Validation.class);
    when(validation3.getType()).thenReturn(MedicationDto.class);
    factory = new ValidationFactory(List.of(validation1, validation2, validation3));
  }

  @Test
  void getValidations() {
    List<Validation<DroneDto>> droneValidations = factory.getValidations(DroneDto.class);
    assertNotNull(droneValidations);
    assertTrue(droneValidations.contains(validation1));
    assertTrue(droneValidations.contains(validation2));

    List<Validation<MedicationDto>> medicationValidations = factory.getValidations(
        MedicationDto.class);
    assertNotNull(medicationValidations);
    assertTrue(medicationValidations.contains(validation3));
  }
}