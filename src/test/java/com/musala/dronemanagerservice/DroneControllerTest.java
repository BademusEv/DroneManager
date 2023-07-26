package com.musala.dronemanagerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.dronemanagerservice.model.constant.Model;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
