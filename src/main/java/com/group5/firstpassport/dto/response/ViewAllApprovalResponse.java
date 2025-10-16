package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.ResultType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewAllApprovalResponse {
  private Long formId;
  private ResultType result;
  private LocalDateTime approvedAt;
  private String approverBy;
}
