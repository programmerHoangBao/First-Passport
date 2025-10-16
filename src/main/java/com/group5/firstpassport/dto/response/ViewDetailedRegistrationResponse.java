package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group5.firstpassport.enums.GenderType;
import com.group5.firstpassport.enums.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewDetailedRegistrationResponse {
    private Long id;
    private String identityNumber;
    private String fullName;
    private String address;
    private GenderType gender;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private StatusType status;
}
