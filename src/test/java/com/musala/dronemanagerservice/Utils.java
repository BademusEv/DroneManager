package com.musala.dronemanagerservice;

import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.entiry.Drone;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

@UtilityClass
public class Utils {

    public Drone getStockDrone(){
        return Drone.builder()
                .serialNumber("fwoife_w23tgjwio_2342_efwk")
                .model(Model.LIGHTWEIGHT)
                .weightLimit(500)
                .state(State.IDLE)
                .build();
    }

    public String getBase64Resource(String fileName) throws IOException, URISyntaxException {
        URL url = Objects.requireNonNull(Utils.class.getClassLoader().getResource(fileName));
        byte[] file = Files.readAllBytes(Path.of(url.toURI()));
        return Base64.getEncoder().encodeToString(file);
    }
}
