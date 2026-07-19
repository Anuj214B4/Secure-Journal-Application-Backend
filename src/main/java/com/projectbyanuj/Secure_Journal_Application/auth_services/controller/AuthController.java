package com.projectbyanuj.Secure_Journal_Application.auth_services.controller;

import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.*;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.Role;
import com.projectbyanuj.Secure_Journal_Application.auth_services.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.createUser(signupRequest, Role.USER));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponse<Void>> createAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.createUser(signupRequest, Role.ADMIN));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody SigningRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshAccessToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }
}
