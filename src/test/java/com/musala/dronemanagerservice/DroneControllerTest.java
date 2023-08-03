package com.musala.dronemanagerservice;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.dronemanagerservice.controller.DroneController;
import com.musala.dronemanagerservice.exception.DroneNotFoundException;
import com.musala.dronemanagerservice.exception.EntityAlreadyExistException;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.BatteryDto;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.service.DroneManagerService;
import java.util.Collections;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(DroneController.class)
class DroneControllerTest {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private DroneManagerService service;

  @Test
  @SneakyThrows
  void testSuccessfulDroneRegistration() {
    RegisterDroneDto registerDroneDto = new RegisterDroneDto("3tgwear32egwe_342twwa_32tr",
        Model.LIGHTWEIGHT, 500);
    doNothing().when(service).registerDrone(registerDroneDto);
    mockMvc.perform(post("/drone")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDroneDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @SneakyThrows
  void testInvalidDroneRegistrationThrowsException() {
    RegisterDroneDto registerDroneDto = new RegisterDroneDto("3tgwear32egwe_342twwa_32tr",
        Model.LIGHTWEIGHT, null);
    doNothing().when(service).registerDrone(registerDroneDto);
    mockMvc.perform(post("/drone")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDroneDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Weight limit must be filled"))
        .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
  }

  @Test
  @SneakyThrows
  void testExistingDroneRegistrationThrowsException() {
    RegisterDroneDto registerDroneDto = new RegisterDroneDto("3tgwear32egwe_342twwa_32tr",
        Model.LIGHTWEIGHT, 500);
    doThrow(new EntityAlreadyExistException("Drone", "3tgwear32egwe_342twwa_32tr")).when(service)
        .registerDrone(registerDroneDto);
    mockMvc.perform(post("/drone")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDroneDto)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errorCode").value("DUPLICATION_ENTITY"))
        .andExpect(
            jsonPath("$.message").value("Drone with id 3tgwear32egwe_342twwa_32tr already exist"));
  }

  @Test
  @SneakyThrows
  void testUnknownExceptionThrowing() {
    RegisterDroneDto registerDroneDto = new RegisterDroneDto("3tgwear32egwe_342twwa_32tr",
        Model.LIGHTWEIGHT, 500);
    doThrow(new RuntimeException("exception message")).when(service)
        .registerDrone(registerDroneDto);
    mockMvc.perform(post("/drone")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDroneDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.errorCode").value("UNKNOWN_ERROR"))
        .andExpect(
            jsonPath("$.message").value("exception message"));
  }

  @Test
  @SneakyThrows
  void testSuccessfulDroneLoading() {
    String base64Image = Utils.getBase64Resource("medicationsPhoto.png");
    Set<MedicationDto> medications = Set.of(new MedicationDto("A", 41, "3142DWR", base64Image),
        new MedicationDto("B", 35, "DWR234R", base64Image));
    DroneDto droneDto = new DroneDto("feqwre1421", Model.LIGHTWEIGHT, 500, (byte) 60,
        State.LOADED,
        medications);
    when(service.loadDrone(anyString(), anySet())).thenReturn(droneDto);

    mockMvc.perform(put("/drone/load/feqwre1421")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(medications)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testNotExistingDroneLoadingThrowsException() {
    Set<MedicationDto> medications = Set.of(new MedicationDto("A", 41, "3142DWR", "base64Image"));
    when(service.loadDrone(anyString(), anySet())).thenThrow(
        new DroneNotFoundException("feqwre1421"));

    mockMvc.perform(put("/drone/load/feqwre1421")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(medications)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("ENTITY_NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Drone with serialNumber feqwre1421 doesn't exist"));
  }

  @Test
  @SneakyThrows
  void testLoadingInvalidMedicationsThrowsException() {
    Set<MedicationDto> medications = Set.of(new MedicationDto("ABC", 23, "EW22QQ", "base64image"),
        new MedicationDto("A  ", 24, "2312", "base64image"));

    mockMvc.perform(put("/drone/load/feqwre1421")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(medications)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.message").value(
            "loadDrone.medication[].name: Name must contain only letters, numbers, ‘-‘, ‘_’"));
  }

  @Test
  @SneakyThrows
  void testGettingLoadedItems() {
    var medications = Set.of(
        new MedicationDto("A", 41, "3142dwr", "base64Image"),
        new MedicationDto("B", 35, "dwr234r", "base64Image")
    );
    when(service.checkLoading("vslvpdjop3241adf")).thenReturn(medications);

    mockMvc.perform(get("/drone/checkLoading/vslvpdjop3241adf"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(medications)));
  }

  @Test
  @SneakyThrows
  void testGettingNotExistingDroneLoadedItemsThrowsException() {
    when(service.checkLoading("vslvpdjop3241adf")).thenThrow(
        new DroneNotFoundException("vslvpdjop3241adf"));

    mockMvc.perform(get("/drone/checkLoading/vslvpdjop3241adf"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("ENTITY_NOT_FOUND"))
        .andExpect(
            jsonPath("$.message")
                .value("Drone with serialNumber vslvpdjop3241adf doesn't exist"));
  }

  @Test
  @SneakyThrows
  void testGettingAvailableDrones() {
    Set<DroneDto> drones = Set.of(
        new DroneDto("323dsdaq3", Model.LIGHTWEIGHT, 500, (byte) 30, State.IDLE,
            Collections.emptySet()),
        new DroneDto("3423efaFS24", Model.LIGHTWEIGHT, 500, (byte) 50, State.LOADING,
            Collections.emptySet()));
    when(service.getAvailableDrones()).thenReturn(drones);

    mockMvc.perform(get("/drone/available"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[*].serialNumber", containsInAnyOrder("323dsdaq3", "3423efaFS24")));
  }

  @Test
  @SneakyThrows
  void checkBattery() {
    when(service.checkBattery("342rFSD32")).thenReturn(new BatteryDto("342rFSD32", (byte) 30));

    mockMvc.perform(get("/drone/battery/342rFSD32"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.batteryCapacity").value(30))
        .andExpect(jsonPath("$.serialNumber").value("342rFSD32"));
  }
}
