package com.musala.dronemanagerservice.exception;

public class DroneNotFoundException extends ResourceNotFoundException {

    private static final String RESOURCE_NAME = "Drone";
    private static final String ID_NAME = "serialNumber";

    public DroneNotFoundException(String serialNumber) {
        super(RESOURCE_NAME, ID_NAME, serialNumber);
    }
}
