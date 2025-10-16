package com.group5.firstpassport.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.MessageCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
  private MessageCode messageCode;
  private LocalDateTime timestamp;
}
