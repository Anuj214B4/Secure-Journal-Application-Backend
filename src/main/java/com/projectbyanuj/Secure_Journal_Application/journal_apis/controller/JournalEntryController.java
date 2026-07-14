package com.projectbyanuj.Secure_Journal_Application.journal_apis.controller;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalEntryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalEntryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entries")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<JournalEntryResponse> createJournalEntry(@RequestBody JournalEntryRequest request) {
        return new ResponseEntity<>(journalEntryService.createJournalEntry(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntryResponse>> getJournalEntries() {
        return ResponseEntity.ok(journalEntryService.getJournalEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryResponse> getJournalEntry(@PathVariable Long id) {
        return ResponseEntity.ok(journalEntryService.getJournalEntry(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryResponse> updateJournalEntry(@PathVariable Long id,
                                                                   @RequestBody JournalEntryRequest request) {
        return ResponseEntity.ok(journalEntryService.updateJournalEntry(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJournalEntry(@PathVariable Long id) {
        if(journalEntryService.deleteJournalEntry(id)){
            return new ResponseEntity<>("Journal entry has been deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal entry not found.", HttpStatus.NOT_FOUND);
    }
}
