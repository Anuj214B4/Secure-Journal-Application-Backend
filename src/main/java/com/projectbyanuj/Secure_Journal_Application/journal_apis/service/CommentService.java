package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CommentRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest);

    List<CommentResponse> getComments();

    CommentResponse updateComment(Long id, CommentRequest commentRequest);

    boolean createComment(Long id);
}
