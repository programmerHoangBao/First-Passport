package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.ViewAllSendFromXDResponse;
import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.MessageResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.dto.response.ViewDetailedApprovalResponse;

public interface IApprovalService {
  ApprovalResponse approval(Long approvalId, Long approvalBy);
  Page<ViewAllApprovalResponse> viewAllApprovalByResult(String resultStr, int pageNumber, int pageSize);
  Page<ViewAllSendFromXDResponse> viewAllApprovalByResultIsNull(int pageNumber, int pageSize);
  ViewDetailedApprovalResponse viewDetailedApproval(Long id);
  MessageResponse rejectApproval(Long approvalId, Long approverBy);
}
