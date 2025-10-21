package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.*;
import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.request.RegistrationRequest;

public interface IRegistrationService {
  RegistrationResponse registration(RegistrationRequest registrationRequest);
  Page<ViewAllRegistrationResponse> findAllByStatus(String statusStr, int pageSize, int pageNumber);
  ViewDetailedRegistrationResponse findDetailed(Long id);
  MessageResponse sendFromToXD(Long formId, Long userId);
  MessageResponse rejectForm(Long formId);

}
