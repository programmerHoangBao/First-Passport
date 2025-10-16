package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.request.LoginRequest;
import com.group5.firstpassport.dto.response.LoginResponse;
import com.group5.firstpassport.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

  AuthServiceImpl authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest reqDTO) {
    return ResponseEntity.ok(authService.login(reqDTO));
  }
}
