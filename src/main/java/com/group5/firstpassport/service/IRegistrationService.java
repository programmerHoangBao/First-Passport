package com.group5.firstpassport.service;

import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;
import com.group5.firstpassport.dto.response.ViewDetailedRegistrationResponse;

public interface IRegistrationService {
  RegistrationResponse registration(RegistrationRequest registrationRequest);
  Page<ViewAllRegistrationResponse> findAllByStatus(String statusStr, int pageSize, int pageNumber);
  ViewDetailedRegistrationResponse findDetailed(Long id);
}
