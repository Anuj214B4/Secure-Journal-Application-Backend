package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalEntryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalEntryResponse;

import java.util.List;

public interface JournalEntryService {
    JournalEntryResponse createJournalEntry(JournalEntryRequest request);

    List<JournalEntryResponse> getJournalEntries();

    JournalEntryResponse getJournalEntry(Long id);

    JournalEntryResponse updateJournalEntry(Long id, JournalEntryRequest request);

    boolean deleteJournalEntry(Long id);
}
