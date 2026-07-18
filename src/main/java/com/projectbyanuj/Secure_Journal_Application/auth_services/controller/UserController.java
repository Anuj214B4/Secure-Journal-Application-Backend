package com.projectbyanuj.Secure_Journal_Application.auth_services.controller;

import com.projectbyanuj.Secure_Journal_Application.auth_services.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(Map.of(
                "name", currentUser.getFullName(),
                "role", Objects.requireNonNull(currentUser.getAuthorities().iterator().next().getAuthority()).replace("ROLE_", "")
        ));
    }
}
