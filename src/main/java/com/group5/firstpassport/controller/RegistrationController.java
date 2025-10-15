package com.group5.firstpassport.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;
import com.group5.firstpassport.service.impl.RegistrationServiceImpl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
  @GetMapping("/view-all-registration")
  public ResponseEntity<Page<ViewAllRegistrationResponse>> findAll(@RequestParam int pageSize, @RequestParam int pageNumber) {
    return ResponseEntity.ok(registrationService.findAll(pageSize, pageNumber));
  }
}
