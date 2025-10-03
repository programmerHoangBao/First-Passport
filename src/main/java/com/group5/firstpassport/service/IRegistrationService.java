package com.group5.firstpassport.service;

import org.springframework.data.domain.Page;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;

public interface IRegistrationService {
    RegistrationResponse registration(RegistrationRequest registrationRequest);
    Page<ViewAllRegistrationResponse> findAll(int pageSize, int pageNumber);
}
