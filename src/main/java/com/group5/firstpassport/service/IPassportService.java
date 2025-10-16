package com.group5.firstpassport.service;

import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.response.CreatePassportResponse;
import com.group5.firstpassport.dto.response.ViewAllRequestStoreResponse;

public interface IPassportService {
  CreatePassportResponse createPassport(Long approvalId, Long userId);
  Page<ViewAllRequestStoreResponse> viewAllRequestStore(int pageNumber, int pageSize);
}
