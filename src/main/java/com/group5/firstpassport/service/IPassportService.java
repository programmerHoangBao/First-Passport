package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.CreatePassportResponse;

public interface IPassportService {
  CreatePassportResponse createPassport(Long approvalId, Long userId);
}
