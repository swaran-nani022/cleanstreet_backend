package com.cleanstreet.backend.service;

public interface VoteService {

    String voteComplaint(
            String complaintId,
            String voteType,
            String userEmail
    );
}