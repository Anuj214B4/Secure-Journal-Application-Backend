package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.auth_services.entity.AppUser;
import com.projectbyanuj.Secure_Journal_Application.auth_services.repository.UserRepository;
import com.projectbyanuj.Secure_Journal_Application.auth_services.service.CustomUserDetails;
import com.projectbyanuj.Secure_Journal_Application.exceptipns.ResourceNotFoundException;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Journal;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers.JournalMapper;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    @Override
    public JournalResponse createJournal(JournalRequest request, CustomUserDetails currentUser) {

        AppUser owner = userRepository
                .findUserByEmail(currentUser.getEmail())
                .orElseThrow();

        Journal journal = JournalMapper.mapToEntity(request);
        journal.setOwner(owner);

        return JournalMapper.mapToResponse(
                journalRepository.save(journal)
        );
    }

    @Override
    public List<JournalResponse> getAllJournals(CustomUserDetails user) {
        List<Journal> journals = journalRepository.findAllByOwnerUserId(user.getUserId());
        if (!journals.isEmpty())
            return journals.stream().map(JournalMapper::mapToResponse).toList();
        else
            return List.of();
    }

    @Override
    public JournalResponse getJournalById(Long id, CustomUserDetails currentUser) {
        Journal journal = journalRepository.findByIdAndOwnerUserId(id, currentUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Journal not exists with id :"+id));
        return JournalMapper.mapToResponse(journal);
    }

    @Override
    public JournalResponse updateJournalById(JournalRequest request, Long id, CustomUserDetails currentUser) {
        Journal existingJournal = journalRepository.findByIdAndOwnerUserId(id, currentUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Journal not exists with id :"+id));

        if (!request.getTitle().isEmpty())
            existingJournal.setTitle(request.getTitle());
        if(!request.getDescription().isEmpty())
            existingJournal.setDescription(request.getDescription());

        journalRepository.save(existingJournal);

        return JournalMapper.mapToResponse(existingJournal);
    }

    @Override
    public boolean deleteJournalById(Long id, CustomUserDetails currentUser) {
        Optional<Journal> journal = journalRepository.findByIdAndOwnerUserId(id, currentUser.getUserId());
        if (journal.isPresent()) {
            journalRepository.delete(journal.get());
            return true;
        }
        return false;
    }
}
