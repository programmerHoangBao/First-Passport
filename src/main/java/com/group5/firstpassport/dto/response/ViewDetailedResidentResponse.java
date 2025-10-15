package com.group5.firstpassport.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewDetailedResidentResponse {
  private String identityNumber;
  private String fullName;
  private LocalDate dateOfBirth;
  private GenderType gender;
  private String address;
  private String phone;
  private String email;
}
