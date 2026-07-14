package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.exceptipns.ResourceNotFoundException;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalEntryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalEntryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Category;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Journal;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.JournalEntry;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Tag;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers.JournalEntryMapper;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.CategoryRepository;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.JournalEntryRepository;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.JournalRepository;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JournalEntryServiceImpl implements JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final JournalRepository journalRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public JournalEntryResponse createJournalEntry(JournalEntryRequest request) {

        Journal journal = journalRepository.findById(request.getJournalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Journal not found."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        JournalEntry journalEntry = JournalEntryMapper.mapToEntity(
                request,
                journal,
                category
        );

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
        if (tags.size() != request.getTagIds().size()) {
            throw new ResourceNotFoundException("One or more tags not found.");
        }
        journalEntry.setTags(tags);

        journalEntry = journalEntryRepository.save(journalEntry);

        return JournalEntryMapper.mapToResponse(journalEntry);
    }

    @Override
    public List<JournalEntryResponse> getJournalEntries() {
        List<JournalEntry> journalEntries = journalEntryRepository.findAll();
        if (!journalEntries.isEmpty()) {
            return journalEntries.stream().map(JournalEntryMapper::mapToResponse).toList();
        }
        return List.of();
    }

    @Override
    public JournalEntryResponse getJournalEntry(Long id) {
        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found."));
        return JournalEntryMapper.mapToResponse(journalEntry);
    }

    @Override
    public JournalEntryResponse updateJournalEntry(Long id, JournalEntryRequest request) {

        JournalEntry existingEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found."));

        existingEntry.setTitle(request.getTitle());
        existingEntry.setContent(request.getContent());

        Journal journal = journalRepository.findById(request.getJournalId())
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found."));
        existingEntry.setJournal(journal);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        existingEntry.setCategory(category);


        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            existingEntry.setTags(tags);
        }

        JournalEntry updatedEntry = journalEntryRepository.save(existingEntry);

        return JournalEntryMapper.mapToResponse(updatedEntry);
    }

    @Override
    public boolean deleteJournalEntry(Long id) {
        return journalEntryRepository.findById(id)
                .map(entry -> {
                    journalEntryRepository.delete(entry);
                    return true;
                })
                .orElse(false);
    }
}
