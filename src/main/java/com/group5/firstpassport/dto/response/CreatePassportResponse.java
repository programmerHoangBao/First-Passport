package com.group5.firstpassport.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.GenderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CreatePassportResponse {
  private Long id;
  private String fullName;
  private String address;
  private GenderType gender;
  private String phone;
  private String email;
}
