package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.exceptipns.ResourceNotFoundException;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CommentRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CommentResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Comment;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers.CommentMapper;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        return CommentMapper.mapToResponse(
                commentRepository.save(CommentMapper.mapToEntity(commentRequest)));
    }

    @Override
    public List<CommentResponse> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentMapper::mapToResponse)
                .toList();
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found.")
        );

        if (existingComment.getComment() != null) {
            existingComment.setComment(commentRequest.getComment());
        }
        return CommentMapper.mapToResponse(commentRepository.save(existingComment));
    }

    @Override
    public boolean createComment(Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return true;
                }).orElse(false);
    }
}
