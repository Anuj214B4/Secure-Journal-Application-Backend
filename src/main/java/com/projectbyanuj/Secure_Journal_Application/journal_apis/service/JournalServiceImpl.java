package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

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

    @Override
    public JournalResponse createJournal(JournalRequest request) {
        return JournalMapper.mapToResponse(
                journalRepository.save(JournalMapper.mapToEntity(request))
        );
    }

    @Override
    public List<JournalResponse> getAllJournals() {
        List<Journal> journals = journalRepository.findAll();
        if (!journals.isEmpty())
            return journals.stream().map(JournalMapper::mapToResponse).toList();
        else
            return List.of();
    }

    @Override
    public JournalResponse getJournalById(Long id) {
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal not exists with id :"+id));
        return JournalMapper.mapToResponse(journal);
    }

    @Override
    public JournalResponse updateJournalById(JournalRequest request, Long id) {
        Journal existingJournal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal not exists with id :"+id));

        if (!existingJournal.getTitle().isEmpty())
            existingJournal.setTitle(request.getTitle());
        if(!existingJournal.getDescription().isEmpty())
            existingJournal.setDescription(request.getDescription());

        return JournalMapper.mapToResponse(existingJournal);
    }

    @Override
    public boolean deleteJournalById(Long id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            journalRepository.delete(journal.get());
            return true;
        }
        return false;
    }
}
