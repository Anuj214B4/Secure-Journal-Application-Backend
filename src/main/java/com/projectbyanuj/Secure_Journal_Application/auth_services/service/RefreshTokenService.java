package com.projectbyanuj.Secure_Journal_Application.auth_services.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.RefreshToken;
import com.projectbyanuj.Secure_Journal_Application.auth_services.exception.TokenRefreshException;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.RefreshTokenRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Value("${jwt.refresh-token.expiration-ms}")
    private long refreshTokenExpirationMs;

    @PostConstruct
    public void init() {
        System.out.println("Refresh token expiration: " + refreshTokenExpirationMs);
    }

    @Transactional
    public RefreshToken generateRefreshToken(AppUser user) {

        //one active refresh token per user — revoke any previous ones first
        this.refreshTokenRepository.revokeAllByUser(user);

        Instant now = Instant.now();
        Instant expiry = now.plusMillis(refreshTokenExpirationMs);

        System.out.println("Created: " + now);
        System.out.println("Expires: " + expiry);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(expiry)
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);

    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository
                .findByToken(token).orElseThrow(() -> new TokenRefreshException("Refresh token not found."));
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.isRevoked()) {
            throw new TokenRefreshException("Refresh token was revoked. Please sign in again.");
        }
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenRefreshException("Refresh token expired. Please sign in again.");
        }
        return refreshToken;
    }

    @Transactional
    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = findByToken(token);
        refreshToken.setRevoked(true);
        this.refreshTokenRepository.save(refreshToken);
    }


}
