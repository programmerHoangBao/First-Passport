package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.GenderType;
import com.group5.firstpassport.enums.ResultType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewDetailedApprovalResponse {
  Long id;
  private Long formId;
  private String identityNumber;
  private String fullName;
  private String address;
  private GenderType gender;
  private String phone;
  private String email;
  private LocalDateTime createdFormAt;
  private String xtBy;
  private ResultType result;
  private LocalDateTime approvedAt;
  private String approverBy;
  private String createdBY;
  private LocalDateTime createdAt;
}
