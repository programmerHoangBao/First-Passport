package com.group5.firstpassport.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewAllLogResponse {
  private Long id;
  private String actor;
  private RoleType role;
  private String action;
  private LocalDateTime eventTime;
  private String policyName;
  private String objectName;
  private String sqlText;
}
