package com.projectbyanuj.Secure_Journal_Application.auth_services.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AppUser appUser = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email : " + email));

        return new CustomUserDetails(appUser);
    }
}
