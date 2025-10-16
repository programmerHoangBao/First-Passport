package com.group5.firstpassport.service;

import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;

public interface IApprovalService {
  ApprovalResponse approval(ApprovalRequest approvalRequest);
  Page<ViewAllApprovalResponse> viewAllApproval(String resultStr, int pageNumber, int pageSize);
}
