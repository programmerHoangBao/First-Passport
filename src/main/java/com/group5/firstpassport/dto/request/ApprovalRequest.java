package com.group5.firstpassport.dto.request;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.ResultType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {
  @NotNull(message = "Form ID is required")
  private Long formId;
  private ResultType result = ResultType.APPROVED;
  private LocalDateTime approvedAt = LocalDateTime.now();
  @NotNull(message = "Approver ID is required")
  private Long approverBy;
}
