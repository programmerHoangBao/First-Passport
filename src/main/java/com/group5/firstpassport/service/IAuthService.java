package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.request.LoginRequest;
import com.group5.firstpassport.dto.response.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest loginRequest);
}
