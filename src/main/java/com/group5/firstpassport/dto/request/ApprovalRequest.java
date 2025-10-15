package com.group5.firstpassport.dto.request;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.ResultType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {
  @NotBlank(message = "Form ID is required")
  private Long formId;
  private ResultType result = ResultType.APPROVED;
  private LocalDateTime approvedAt = LocalDateTime.now();
  @NotBlank(message = "Address is required")
  private String address;
  @NotBlank(message = "Approver ID is required")
  private Long approverBy;
}
