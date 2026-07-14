package com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CommentRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CommentResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Comment;

public class CommentMapper {
    public static Comment mapToEntity(CommentRequest request) {
        return Comment.builder()
                .comment(request.getComment())
                .build();
    }

    public static CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .build();
    }
}
