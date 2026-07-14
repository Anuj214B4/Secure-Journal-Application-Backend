package com.projectbyanuj.Secure_Journal_Application.journal_apis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;
}
