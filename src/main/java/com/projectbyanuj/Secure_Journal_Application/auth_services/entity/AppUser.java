package com.projectbyanuj.Secure_Journal_Application.auth_services.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appUsers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
