package com.projectbyanuj.Secure_Journal_Application.auth_services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String role;
}
