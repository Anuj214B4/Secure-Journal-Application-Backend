package com.projectbyanuj.Secure_Journal_Application.journal_apis.entity;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "journals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;
}
