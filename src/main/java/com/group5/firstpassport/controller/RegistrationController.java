package com.group5.firstpassport.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;
import com.group5.firstpassport.dto.response.ViewDetailedRegistrationResponse;
import com.group5.firstpassport.service.impl.RegistrationServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/first-passport")
@Validated
public class RegistrationController {
  RegistrationServiceImpl registrationService;

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponse> registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
    return ResponseEntity.ok(registrationService.registration(registrationRequest));
  }

  @GetMapping("/view-all-registration")
  public ResponseEntity<Page<ViewAllRegistrationResponse>> findAll(
    @RequestParam
    @Pattern(
        regexp = "^(?i)(PENDING|VERIFIED|REJECTED)$", 
        message = "Status must be one of: PENDING, VERIFIED, REJECTED"
    )
    String status,
    @RequestParam(defaultValue = "10") int pageSize, 
    @RequestParam(defaultValue = "0") int pageNumber) {
    return ResponseEntity.ok(registrationService.findAllByStatus(status, pageSize, pageNumber));
  }

  @GetMapping("/view-detail-registration")
  public ResponseEntity<ViewDetailedRegistrationResponse> findDetailed(@RequestParam Long id) {
    return ResponseEntity.ok(registrationService.findDetailed(id));
  }
}
