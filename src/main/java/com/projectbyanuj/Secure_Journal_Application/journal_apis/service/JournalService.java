package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.service.CustomUserDetails;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;

import java.util.List;

public interface JournalService {
    JournalResponse createJournal(JournalRequest request, CustomUserDetails currentUser);
    List<JournalResponse> getAllJournals(CustomUserDetails user);
    JournalResponse getJournalById(Long id, CustomUserDetails currentUser);
    JournalResponse updateJournalById(JournalRequest request, Long id, CustomUserDetails currentUser);
    boolean deleteJournalById(Long id, CustomUserDetails currentUser);
}
