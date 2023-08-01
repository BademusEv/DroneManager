package com.musala.dronemanagerservice.model.dto;

import com.musala.dronemanagerservice.model.constant.ErrorCode;

public record ErrorMessage(String message, ErrorCode errorCode) {

}
