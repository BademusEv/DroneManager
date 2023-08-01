package com.musala.dronemanagerservice.model.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Model {
  LIGHTWEIGHT("Lightweight"),
  MIDDLEWEIGHT("Middleweight"),
  CRUISERWEIGHT("Cruiserweight"),
  HEAVYWEIGHT("Heavyweight");

  @JsonValue
  private final String name;
}
