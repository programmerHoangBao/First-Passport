package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.request.LoginRequest;
import com.group5.firstpassport.service.Impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccessController {

    AuthServiceImpl authService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest reqDTO) {

        return ResponseEntity.ok(authService.login(reqDTO));
    }
}
