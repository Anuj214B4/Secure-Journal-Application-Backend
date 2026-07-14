package com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalResponse {
    private Long id;

    private String title;

    private String description;
}
