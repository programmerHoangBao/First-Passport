package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.service.impl.RegistrationServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/first-passport")
public class RegistrationController {
    RegistrationServiceImpl registrationService;
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(registrationService.registration(registrationRequest));
    }
}
