package com.group5.firstpassport.enums;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
  // Bad request
  INVALID_HTTP(1100, "Invalid HTTP Request", HttpStatus.METHOD_NOT_ALLOWED),
  INVALID_PARAMETER_TYPE(1101, "Invalid parameter type", HttpStatus.BAD_REQUEST),
  INVALID_REQUEST_BODY(1102, "Invalid request body format", HttpStatus.BAD_REQUEST),
  MISSING_REQUIRED_PARAMETER(1103, "Missing required request parameter", HttpStatus.BAD_REQUEST),
  UNSUPPORTED_MEDIA_TYPE(1104, "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
  RESOURCE_NOT_FOUND(1105, "Requested resource not found", HttpStatus.NOT_FOUND),
  SERVICE_UNAVAILABLE(1106, "The service is temporarily unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE),
  ACCESS_DENIED(1107, "You do not have permission to perform this action!", HttpStatus.FORBIDDEN),
  USER_NO_EXIST(1108, "User with this id does not exist", HttpStatus.NOT_FOUND),
  LOGIN_FAILED(1109, "Login failed", HttpStatus.UNAUTHORIZED),
  RESIDENT_NOT_FOUND(1110, "Resident not found", HttpStatus.NOT_FOUND),
  NO_DATA(1111, "No data", HttpStatus.NOT_FOUND),
  FORM_REGISTRATION_NOT_FOUND(1111, "Form registration not found", HttpStatus.NOT_FOUND),
  RESIDENT_NO_EXIST(1112, "Resident not found", HttpStatus.NOT_FOUND),
  SEND_FROM_REGISTRATION_FALIED(1113, "Send from registration failed.", HttpStatus.BAD_REQUEST),
  APPROVAL_NO_EXISTS(1114, "Approval does not exist.", HttpStatus.NOT_FOUND),
  UNVERIFIED_INFORMATION(1115, "Information is unverified or does not exist.", HttpStatus.NOT_FOUND),
  SAVE_PASSPORT_FAILED(1116, "Failed to save passport information!", HttpStatus.BAD_REQUEST),
  REJECT_FORM_FAILED(1117, "Failed to reject the passport application form.", HttpStatus.BAD_REQUEST),
  REJECT_REQUEST_STORE_FAILED(1118, "The request to deny passport creation failed.", HttpStatus.BAD_REQUEST),
  APPROVAL_FAILED(1119, "Passport registration information verification failed!", HttpStatus.BAD_REQUEST),
  REJECT_APPROVAL_FAILED(1120, "Failed to deny the verification request.", HttpStatus.BAD_REQUEST),
  EMAIL_SENDING_FAILED(900, "Failed to send email. Please try again later.", HttpStatus.BAD_REQUEST),
  OTHER_EXCEPTIONS(1999, "System error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final int responseCode;
  private final String message;
  private final HttpStatus httpStatus;
}