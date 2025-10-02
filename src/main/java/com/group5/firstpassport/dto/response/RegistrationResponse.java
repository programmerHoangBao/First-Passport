package com.group5.firstpassport.dto.response;

import com.group5.firstpassport.enums.Gender;
import com.group5.firstpassport.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private Long id;
    private String fullName;
    private String address;
    private Gender gender;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private Status status;
}
