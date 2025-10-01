package com.group5.firstpassport.dto.request;

import com.group5.firstpassport.enums.Gender;
import com.group5.firstpassport.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Identity Number should not be blank")
    @Pattern(regexp = "^\\d{12}$", message = "Identity Number must contain 12 digits")
    private String identityNumber;
    @NotBlank(message = "Full name should not be blank")
    private String fullName;
    @NotBlank(message = "Address should not be blank")
    private String address;
    private Gender gender = Gender.MALE;
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain 10 digits")
    @NotBlank(message = "Phone number should not be blank")
    private String phone;
    @Email(message = "Email address is not formated")
    @NotBlank(message = "Email address should not be blank")
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Status status = Status.PENDING;
}


