package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.response.ViewAllSendFromXDResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.dto.response.ViewDetailedApprovalResponse;
import com.group5.firstpassport.service.impl.ApprovalServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/xd")
@Validated
public class ApprovalController {
  ApprovalServiceImpl approvalService;

  @PostMapping("/approval")
  public ResponseEntity<ApprovalResponse> approval(@Valid @RequestBody ApprovalRequest approvalRequest) {
    return ResponseEntity.ok(approvalService.approval(approvalRequest));
  }

  @GetMapping("/all-approval-by-result")
  public ResponseEntity<Page<ViewAllApprovalResponse>> viewAllApprovalByResult(
    @RequestParam
    @Pattern(
      regexp = "^(?i)(APPROVED|REJECTED)$", 
      message = "Status must be one of: APPROVED, REJECTED"
    )
    String result,
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize
  ) {
    return ResponseEntity.ok(approvalService.viewAllApprovalByResult(result, pageNumber, pageSize));
  }

  @GetMapping("/all-send-from-to-xd")
  public ResponseEntity<Page<ViewAllSendFromXDResponse>> viewAllApprovalByResultIsNull(
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize
  ) {
    return ResponseEntity.ok(approvalService.viewAllApprovalByResultIsNull(pageNumber, pageSize));
  }

  @GetMapping("/view-detail-approval")
  public ResponseEntity<ViewDetailedApprovalResponse> viewDetailedApproval(@RequestParam Long id) {
    return ResponseEntity.ok(approvalService.viewDetailedApproval(id));
  }
}
