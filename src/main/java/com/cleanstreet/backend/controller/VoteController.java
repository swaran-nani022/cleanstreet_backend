package com.cleanstreet.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleanstreet.backend.service.VoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{complaintId}")
    public ResponseEntity<?> voteComplaint(

            @PathVariable String complaintId,

            @RequestParam String type,

            Authentication authentication
    ) {

        String userEmail =
                authentication.getName();

        String response =
                voteService.voteComplaint(
                        complaintId,
                        type,
                        userEmail
                );

        return ResponseEntity.ok(
                Map.of("message", response)
        );
    }
}