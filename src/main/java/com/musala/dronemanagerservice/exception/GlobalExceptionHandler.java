package com.musala.dronemanagerservice.exception;

import com.musala.dronemanagerservice.model.constant.ErrorCode;
import com.musala.dronemanagerservice.model.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DroneOverloadedException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(DroneOverloadedException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ErrorCode.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DroneNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleBadRequestException(DroneNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ErrorCode.ENTITY_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

}