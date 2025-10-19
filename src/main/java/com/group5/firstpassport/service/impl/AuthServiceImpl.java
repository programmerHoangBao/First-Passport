package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.dto.request.LoginRequest;
import com.group5.firstpassport.dto.response.LoginResponse;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IAuthService;
import com.group5.firstpassport.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> existedUser = userRepository.findByUsername(loginRequest.getUsername());

        if (existedUser.isEmpty()) {
            throw new BadRequestException(ErrorCode.USER_NO_EXIST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String userRole = existedUser.get().getRole().name();
            String accessToken = JwtUtil.generateToken(loginRequest.getUsername(), userRole);
            String refreshToken = JwtUtil.generateRefreshToken(loginRequest.getUsername(), userRole);

            return LoginResponse.builder()
                    .userId(existedUser.get().getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        throw new BadRequestException(ErrorCode.LOGIN_FAILED);
    }
}
