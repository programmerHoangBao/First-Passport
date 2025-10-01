package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;

public interface IRegistrationService {
    RegistrationResponse registration(RegistrationRequest registrationRequest);
}
