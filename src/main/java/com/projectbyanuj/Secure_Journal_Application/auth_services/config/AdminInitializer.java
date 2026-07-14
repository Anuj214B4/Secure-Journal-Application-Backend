package com.projectbyanuj.Secure_Journal_Application.auth_services.config;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.Role;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    @Bean
    public CommandLineRunner registerAdmin(){
        return args -> {
            if (!userRepository.existsByRole(Role.ADMIN) &&
                    !userRepository.existsAppUserByEmail("admin@gmail.com")){
                AppUser admin = AppUser.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
                logger.info(" Default admin created successfully !!");
            }
        };
    }
}
