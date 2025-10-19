package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.MessageResponse;
import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.response.ViewAllRequestStoreResponse;

public interface IPassportService {
  MessageResponse createPassport(Long approvalId, Long userId);
  Page<ViewAllRequestStoreResponse> viewAllRequestStore(int pageNumber, int pageSize);
  MessageResponse rejectPassport(Long approvalId, Long userId);
}
