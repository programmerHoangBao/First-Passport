package com.group5.firstpassport.service.Impl;

import com.group5.firstpassport.dto.request.LoginRequest;
import com.group5.firstpassport.dto.response.LoginResponse;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IAuthService;
import com.group5.firstpassport.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {


    UserRepository userRepository;

    AuthenticationManager authenticationManager;

    JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest reqDTO) {


        UserEntity existedUser = userRepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(reqDTO.getUsername()));

        if (existedUser.getId() == null) {

            throw new UsernameNotFoundException("Username not found");

        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqDTO.getUsername(), reqDTO.getPassword()));

        if (authentication.isAuthenticated()) {

            String accessToken = jwtUtil.generateToken(reqDTO.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(reqDTO.getUsername());

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(existedUser.getId())
                    .build();

        }

        return null;
    }
}
