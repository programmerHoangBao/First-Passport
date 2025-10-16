package com.group5.firstpassport.service;

import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.dto.response.ViewDetailedApprovalResponse;

public interface IApprovalService {
  ApprovalResponse approval(ApprovalRequest approvalRequest);
  Page<ViewAllApprovalResponse> viewAllApprovalByResult(String resultStr, int pageNumber, int pageSize);
  Page<ViewAllApprovalResponse> viewAllApprovalByResultIsNull(int pageNumber, int pageSize);
  ViewDetailedApprovalResponse viewDetailedApproval(Long id);
}
