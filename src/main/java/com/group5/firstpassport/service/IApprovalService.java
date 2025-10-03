package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;

public interface IApprovalService {
  ApprovalResponse approval(ApprovalRequest approvalRequest);
}
