package com.projectbyanuj.Secure_Journal_Application.auth_services.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.*;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.RefreshToken;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.Role;
import com.projectbyanuj.Secure_Journal_Application.auth_services.exception.EmailAlreadyExistsException;
import com.projectbyanuj.Secure_Journal_Application.auth_services.exception.InvalidCredentialsException;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.UserRepository;
import com.projectbyanuj.Secure_Journal_Application.auth_services.security.JwtAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthUtil jwtAuthUtil;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public ApiResponse<Void> createUser(SignupRequest signupRequest, Role role) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already in use");
        }

        AppUser user = AppUser.builder().fullName(signupRequest.getFullName()).email(signupRequest.getEmail()).password(passwordEncoder.encode(signupRequest.getPassword())).role(role).build();

        userRepository.save(user);
        return ApiResponse.success("User created successfully.");
    }

    public ApiResponse<AuthResponse> login(SigningRequest request) {
        Authentication authentication = this.doAuthentication(request);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = this.jwtAuthUtil.generateToken(userDetails);

        // Issue a refresh token for the authenticated user.
        RefreshToken refreshToken = this.refreshTokenService.generateRefreshToken(userDetails.appUser());

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .email(userDetails.getEmail())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();

        return ApiResponse.success("Signed in successfully.", authResponse);
    }

    public ApiResponse<AuthResponse> refreshAccessToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(request.getRefreshToken());
        this.refreshTokenService.verifyExpiration(refreshToken);

        AppUser user = refreshToken.getUser();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String newAccessToken = jwtAuthUtil.generateToken(userDetails);

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken()) // same refresh token reused
                .email(userDetails.getEmail())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();

        return ApiResponse.success("Access token refreshed successfully.", authResponse);

    }

    public ApiResponse<Void> logout(RefreshTokenRequest request) {
        this.refreshTokenService.revokeRefreshToken(request.getRefreshToken());
        return ApiResponse.success("Logged out successfully.");
    }

    private Authentication doAuthentication(SigningRequest signingRequest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signingRequest.getEmail(), signingRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
    }
}
