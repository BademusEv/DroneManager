package com.musala.dronemanagerservice.exception;

import com.musala.dronemanagerservice.model.constant.ErrorCode;
import com.musala.dronemanagerservice.model.dto.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DroneOverloadedException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(DroneOverloadedException ex) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ErrorCode.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDroneNotFoundException(DroneNotFoundException ex) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ErrorCode.ENTITY_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage());
        var message = ex.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(new ErrorMessage(message, ErrorCode.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException ex) {
        log.debug(ex.getMessage());
        var message = ex.getConstraintViolations().stream().map(constraintViolation -> constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage()).collect(Collectors.joining("; "));
        return new ResponseEntity<>(new ErrorMessage(message, ErrorCode.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleUnknownException(RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(ex.getLocalizedMessage(), ErrorCode.UNKNOWN_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
