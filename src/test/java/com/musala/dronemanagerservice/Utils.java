package com.musala.dronemanagerservice;

import com.musala.dronemanagerservice.model.constant.Model;
import com.musala.dronemanagerservice.model.constant.State;
import com.musala.dronemanagerservice.model.dto.DroneDto;
import com.musala.dronemanagerservice.model.entiry.Drone;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class Utils {

    public Drone getStockDrone() {
        return Drone.builder()
                .serialNumber(UUID.randomUUID().toString())
                .model(Model.LIGHTWEIGHT)
                .weightLimit(500)
                .state(State.IDLE)
                .build();
    }

    public Set<Drone> getStockDrones(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getStockDrone())
                .collect(Collectors.toSet());
    }

    public Set<DroneDto> getStockDroneDto(Set<String> serialNumbers) {
        return serialNumbers.stream()
                .map(Utils::getStockDroneDto)
                .collect(Collectors.toSet());
    }

    public DroneDto getStockDroneDto(String serialNumber) {
        return new DroneDto(serialNumber, Model.LIGHTWEIGHT, 500,
                (byte)60, State.IDLE, new HashSet<>());
    }

    public DroneDto getStockDroneDto() {
        return new DroneDto(UUID.randomUUID().toString(), Model.LIGHTWEIGHT, 500,
                (byte)60, State.IDLE, new HashSet<>());
    }

    public String getBase64Resource(String fileName) throws IOException, URISyntaxException {
        URL url = Objects.requireNonNull(Utils.class.getClassLoader().getResource(fileName));
        byte[] file = Files.readAllBytes(Path.of(url.toURI()));
        return Base64.getEncoder().encodeToString(file);
    }
}
