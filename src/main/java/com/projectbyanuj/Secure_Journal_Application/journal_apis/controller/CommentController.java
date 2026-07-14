package com.projectbyanuj.Secure_Journal_Application.journal_apis.controller;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CommentRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CommentResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(commentRequest));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(){
        return ResponseEntity.ok(commentService.getComments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(id,commentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        if (commentService.createComment(id))
            return ResponseEntity.ok("Comment has been deleted.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found.");
    }
}
