package com.group5.firstpassport.enums;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MessageCode {
  SEND_FROM_REGISTRATION_SUCCESS(
      1000, 
      "Verification successfulWaiting for passport application information to be reviewed.", 
      HttpStatus.OK);

  private final int responseCode;
  private final String message;
  private final HttpStatus httpStatus;
}
