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
      HttpStatus.OK),
  REJECT_FORM_REGISTRATION_SUCCESS(
    1001,
    "Passport application form rejected successfully.",
    HttpStatus.OK
  ),
  REJECT_REQUEST_STORE_SUCCESS(
          1002,
          "Reject the passport application request",
          HttpStatus.OK
  );

  private final int responseCode;
  private final String message;
  private final HttpStatus httpStatus;
}
