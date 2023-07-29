package com.musala.dronemanagerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.dronemanagerservice.controller.DroneController;
import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.dto.MedicationDto;
import com.musala.dronemanagerservice.model.dto.RegisterDroneDto;
import com.musala.dronemanagerservice.service.DroneManagerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(DroneController.class)
class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneManagerService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testSuccessfulDroneRegistration() {
        RegisterDroneDto registerDroneDto = new RegisterDroneDto("3tgwear32egwe_342twwa_32tr", Model.LIGHTWEIGHT, 500);
        doNothing().when(service).registerDrone(registerDroneDto);
        mockMvc.perform(post("/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDroneDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void testSuccessfulDroneLoading() {
        String base64Image = Utils.getBase64Resource("medicationsPhoto.png");
        Set<MedicationDto> medications = Set.of(new MedicationDto("A", 41, "3142dwr", base64Image),
                new MedicationDto("B", 35, "dwr234r", base64Image));
        DroneDto droneDto = new DroneDto("feqwre1421", Model.LIGHTWEIGHT, 500, 60.2f, State.LOADED, medications);
        when(service.loadDrone(anyString(), anySet())).thenReturn(droneDto);

        mockMvc.perform(put("/drone/load/feqwre1421")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medications)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetLoadingOfADrone() {
        var medications = Set.of(
                new MedicationDto("A", 41, "3142dwr", "base64Image"),
                new MedicationDto("B", 35, "dwr234r", "base64Image")
        );
        when(service.checkLoading("vslvpdjop3241adf")).thenReturn(medications);

        mockMvc.perform(get("/drone/checkLoading/vslvpdjop3241adf"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(medications)));
    }
}
