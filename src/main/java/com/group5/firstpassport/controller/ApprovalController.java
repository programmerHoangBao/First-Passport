package com.group5.firstpassport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.service.impl.ApprovalServiceImpl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/first-passport")
public class ApprovalController {
  ApprovalServiceImpl approvalServiceImpl;

  @PostMapping("/approval")
  public ResponseEntity<ApprovalResponse> approval(@Valid @RequestBody ApprovalRequest approvalRequest) {
    return ResponseEntity.ok(approvalServiceImpl.approval(approvalRequest));
  }
}
