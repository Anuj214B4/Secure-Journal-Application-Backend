package com.projectbyanuj.Secure_Journal_Application.auth_services.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(AppUser appUser) implements UserDetails {

    public Long getUserId() {
        return appUser.getUserId();
    }

    public String getEmail() {
        return appUser.getEmail();
    }

    public String getFullName() {
        return appUser.getFullName();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}