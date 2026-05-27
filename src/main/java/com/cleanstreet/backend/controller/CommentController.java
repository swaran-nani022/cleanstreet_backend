package com.cleanstreet.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleanstreet.backend.dto.request.CommentRequest;
import com.cleanstreet.backend.dto.response.CommentResponse;
import com.cleanstreet.backend.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Add Comment
    @PostMapping("/{complaintId}")
    public ResponseEntity<CommentResponse> addComment(

            @PathVariable String complaintId,

            @Valid
            @RequestBody CommentRequest request,

            Authentication authentication
    ) {

        String userEmail =
                authentication.getName();

        CommentResponse response =
                commentService.addComment(
                        complaintId,
                        request,
                        userEmail
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // Get Comments
    @GetMapping("/{complaintId}")
    public ResponseEntity<List<CommentResponse>>
    getComments(

            @PathVariable String complaintId
    ) {

        return ResponseEntity.ok(
                commentService
                        .getCommentsByComplaint(
                                complaintId
                        )
        );
    }
}