package com.group5.firstpassport.dto.response;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.GenderType;
import com.group5.firstpassport.enums.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private Long id;
    private String fullName;
    private String address;
    private GenderType gender;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private StatusType status;
}
