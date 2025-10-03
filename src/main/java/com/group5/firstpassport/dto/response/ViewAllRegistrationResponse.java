package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.Gender;
import com.group5.firstpassport.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewAllRegistrationResponse {
  private Long id;
  private String identityNumber;
  private String fullName;
  private String address;
  private Gender gender;
  private String phone;
  private String email;
  private LocalDateTime createdAt;
  private Status status;
}
