package com.projectbyanuj.Secure_Journal_Application.auth_services.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.AuthResponse;
import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.SigningRequest;
import com.projectbyanuj.Secure_Journal_Application.auth_services.dtos.SignupRequest;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.Role;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.UserRepository;
import com.projectbyanuj.Secure_Journal_Application.auth_services.security.JwtAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsService appUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthUtil jwtAuthUtil;

    public String signup(SignupRequest signupRequest) {

        if(userRepository.findUserByEmail(signupRequest.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email is already in use");
        }

        AppUser user = AppUser.builder()
                .fullName(signupRequest.getFullName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User Account Created Successfully.";
    }

    public String createAdmin(SignupRequest signupRequest) {

        if(userRepository.findUserByEmail(signupRequest.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email is already in use");
        }

        AppUser user = AppUser.builder()
                .fullName(signupRequest.getFullName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return "Admin Account Created Successfully.";
    }

    public AuthResponse login(SigningRequest request) {
        this.doAuthentication(request);
        CustomUserDetails userDetails =
                (CustomUserDetails) appUserDetailsService
                        .loadUserByUsername(request.getEmail());

        String token = jwtAuthUtil.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(userDetails.getEmail())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }

    private void doAuthentication(SigningRequest signingRequest) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(signingRequest.getEmail(), signingRequest.getPassword());
        try{
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email Or Password.");
        }
    }
}
