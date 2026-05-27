package com.cleanstreet.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cleanstreet.backend.dto.request.CommentRequest;
import com.cleanstreet.backend.dto.response.CommentResponse;
import com.cleanstreet.backend.entity.Comment;
import com.cleanstreet.backend.entity.Complaint;
import com.cleanstreet.backend.entity.User;
import com.cleanstreet.backend.exception.ResourceNotFoundException;
import com.cleanstreet.backend.repository.CommentRepository;
import com.cleanstreet.backend.repository.ComplaintRepository;
import com.cleanstreet.backend.repository.UserRepository;
import com.cleanstreet.backend.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ComplaintRepository complaintRepository;

    private final UserRepository userRepository;

    @Override
    public CommentResponse addComment(
            String complaintId,
            CommentRequest request,
            String userEmail
    ) {

        // Find User
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        // Check Complaint
        Complaint complaint =
                complaintRepository.findById(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found"
                                )
                        );

        // Create Comment
        Comment comment = Comment.builder()
                .userId(user.getId())
                .complaintId(complaint.getId())
                .content(request.getContent())
                .build();

        Comment savedComment =
                commentRepository.save(comment);

        return mapToResponse(savedComment);
    }

    @Override
    public List<CommentResponse>
    getCommentsByComplaint(String complaintId) {

        return commentRepository
                .findByComplaintId(complaintId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CommentResponse mapToResponse(
            Comment comment
    ) {

        return CommentResponse.builder()
                .id(comment.getId())
                .complaintId(comment.getComplaintId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}