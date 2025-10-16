package com.group5.firstpassport.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.MessageCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
  private MessageCode messageCode;
  private LocalDateTime timestamp;
}
