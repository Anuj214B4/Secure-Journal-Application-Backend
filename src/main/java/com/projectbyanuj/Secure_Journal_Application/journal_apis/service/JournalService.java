package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;

import java.util.List;

public interface JournalService {
    JournalResponse createJournal(JournalRequest request);
    List<JournalResponse> getAllJournals();
    JournalResponse getJournalById(Long id);
    JournalResponse updateJournalById(JournalRequest request, Long id);
    boolean deleteJournalById(Long id);
}
