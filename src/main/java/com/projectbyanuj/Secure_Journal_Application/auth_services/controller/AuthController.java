package com.projectbyanuj.Secure_Journal_Application.auth_services.controller;

import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.AuthResponse;
import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.SigningRequest;
import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.SignupRequest;
import com.projectbyanuj.Secure_Journal_Application.auth_services.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody SignupRequest signupRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.createAdmin(signupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody SigningRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
