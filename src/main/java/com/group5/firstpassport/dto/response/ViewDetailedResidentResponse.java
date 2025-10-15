package com.group5.firstpassport.dto.response;

import com.group5.firstpassport.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewDetailedResidentResponse {
  private String identityNumber;
  private String fullName;
  private LocalDate dateOfBirth;
  private GenderType gender;
  private String address;
  private String phone;
  private String email;
}
