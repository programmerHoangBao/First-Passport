package com.group5.firstpassport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group5.firstpassport.dto.response.CreatePassportResponse;
import com.group5.firstpassport.service.impl.PassportServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/lt")
public class PassportController {
  PassportServiceImpl passportService;

  @PostMapping("/create-passport")
  public ResponseEntity<CreatePassportResponse> createPassport(
    @RequestParam Long approvalId,
    @RequestParam Long usserId
  ) {
    return ResponseEntity.ok(passportService.createPassport(approvalId, usserId));
  }
}
