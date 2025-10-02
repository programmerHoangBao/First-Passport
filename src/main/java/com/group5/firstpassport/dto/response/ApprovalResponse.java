package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalResponse {
  private Long formId;
  private Result result;
  private LocalDateTime approvedAt;
  private String address;
  private Long approverBy;
}