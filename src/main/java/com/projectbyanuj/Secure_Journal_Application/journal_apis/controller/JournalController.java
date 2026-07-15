package com.projectbyanuj.Secure_Journal_Application.journal_apis.controller;

import com.projectbyanuj.Secure_Journal_Application.auth_services.service.CustomUserDetails;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.JournalRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.JournalResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.service.JournalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JournalController {
    private final JournalService journalService;

    //    POST /api/journal
    @PostMapping("/journal")
    public ResponseEntity<JournalResponse> createJournal(@Valid @RequestBody JournalRequest request,
                                                         @AuthenticationPrincipal CustomUserDetails currentUser) {
        return new ResponseEntity<>(journalService.createJournal(request, currentUser), HttpStatus.CREATED);
    }

    //    GET /api/journals
    @GetMapping("/journals")
    public ResponseEntity<List<JournalResponse>> getAllJournals(
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        return ResponseEntity.ok(journalService.getAllJournals(currentUser));
    }

    //    GET /api/journal/{id}
    @GetMapping("/journal/{id}")
    public ResponseEntity<JournalResponse> getJournalById(@PathVariable Long id,
                                                          @AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(journalService.getJournalById(id, currentUser));
    }

    //    PUT /api/journal/{id}
    @PutMapping("/journal/{id}")
    public ResponseEntity<JournalResponse> updateJournalById(@RequestBody JournalRequest request,
                                                             @PathVariable Long id,
                                                             @AuthenticationPrincipal CustomUserDetails currentUser) {
        return new ResponseEntity<>(journalService.updateJournalById(request, id, currentUser), HttpStatus.OK);
    }

    //    DELETE /api/journal/{id}
    @DeleteMapping("/journal/{id}")
    public ResponseEntity<String> deleteJournalById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currentUser) {
        if (journalService.deleteJournalById(id, currentUser)) {
            return new ResponseEntity<>("Journal with id " + id + " deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
    }
}
