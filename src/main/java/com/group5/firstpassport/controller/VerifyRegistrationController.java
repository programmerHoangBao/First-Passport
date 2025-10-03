package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.service.impl.VerifyRegistrationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/xt")
public class VerifyRegistrationController {

    VerifyRegistrationServiceImpl verifyRegistrationService;

    @GetMapping("/registrations")
    public ResponseEntity<Page<RegistrationResponse>> getAllRegistration(Pageable pageable) {
        return ResponseEntity.ok(verifyRegistrationService.getRegistrations(pageable));
    }

    @PostMapping("/verify-registration/{id}")
    public ResponseEntity<RegistrationResponse> verifyRegistration(@PathVariable Long id) {
        return ResponseEntity.ok(verifyRegistrationService.verifyRegistration(id));
    }
}
