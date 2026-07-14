package com.projectbyanuj.Secure_Journal_Application.journal_apis.controller;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.TagRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.TagResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(tagRequest));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable Long id,
                                                 @RequestBody TagRequest tagRequest) {
        return ResponseEntity.ok(tagService.updateTag(id,tagRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        if (tagService.deleteTag(id))
            return ResponseEntity.ok("Tag has been deleted.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tag Not Found.");
    }

}
