package com.group5.firstpassport.exception;

import com.group5.firstpassport.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
  private final ErrorCode errorCode;
  public BadRequestException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
