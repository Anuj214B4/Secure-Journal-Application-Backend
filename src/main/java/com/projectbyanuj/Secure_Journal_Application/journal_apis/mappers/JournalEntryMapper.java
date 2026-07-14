package com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalEntryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalEntryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Category;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Journal;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.JournalEntry;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Tag;

import java.util.stream.Collectors;

public class JournalEntryMapper {
    public static JournalEntry mapToEntity(
            JournalEntryRequest request,
            Journal journal,
            Category category
    ) {
        return JournalEntry.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .journal(journal)
                .category(category)
                .build();
    }

    public static JournalEntryResponse mapToResponse(JournalEntry journalEntry) {
        return JournalEntryResponse.builder()
                .id(journalEntry.getId())
                .title(journalEntry.getTitle())
                .content(journalEntry.getContent())
                .journalId(journalEntry.getJournal().getId())
                .categoryId(journalEntry.getCategory().getId())
                .tags(journalEntry.getTags()
                        .stream()
                        .map(Tag::getTagName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
