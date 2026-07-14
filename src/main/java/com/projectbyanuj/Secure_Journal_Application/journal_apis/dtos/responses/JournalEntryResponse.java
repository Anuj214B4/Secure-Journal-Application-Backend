package com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntryResponse {
    private Long id;
    private String title;
    private String content;
    private Long journalId;
    private Long categoryId;
    private Set<String> tags;

}
