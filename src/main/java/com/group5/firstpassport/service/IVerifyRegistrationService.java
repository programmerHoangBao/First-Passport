package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVerifyRegistrationService {

    RegistrationResponse verifyRegistration(Long id);

    Page<RegistrationResponse> getRegistrations(Pageable pageable);
}
