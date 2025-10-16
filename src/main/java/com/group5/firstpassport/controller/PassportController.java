package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.response.ViewAllRequestStoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/all-request-store")
  public ResponseEntity<Page<ViewAllRequestStoreResponse>> viewAllRequestStore(
          @RequestParam int pageNumber,
          @RequestParam int pageSize) {
    return ResponseEntity.ok(passportService.viewAllRequestStore(pageNumber, pageSize));
  }
}
