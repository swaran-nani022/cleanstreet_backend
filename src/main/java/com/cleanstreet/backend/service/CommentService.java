package com.cleanstreet.backend.service;

import java.util.List;

import com.cleanstreet.backend.dto.request.CommentRequest;
import com.cleanstreet.backend.dto.response.CommentResponse;

public interface CommentService {

    CommentResponse addComment(
            String complaintId,
            CommentRequest request,
            String userEmail
    );

    List<CommentResponse> getCommentsByComplaint(
            String complaintId
    );
}