package com.cleanstreet.backend.service.impl;

import org.springframework.stereotype.Service;

import com.cleanstreet.backend.entity.Complaint;
import com.cleanstreet.backend.entity.User;
import com.cleanstreet.backend.entity.Vote;
import com.cleanstreet.backend.enums.VoteType;
import com.cleanstreet.backend.exception.BadRequestException;
import com.cleanstreet.backend.exception.ResourceNotFoundException;
import com.cleanstreet.backend.repository.ComplaintRepository;
import com.cleanstreet.backend.repository.UserRepository;
import com.cleanstreet.backend.repository.VoteRepository;
import com.cleanstreet.backend.service.VoteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final ComplaintRepository complaintRepository;

    private final UserRepository userRepository;

    @Override
    public String voteComplaint(
            String complaintId,
            String voteType,
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

        // Find Complaint
        Complaint complaint =
                complaintRepository.findById(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found"
                                )
                        );

        // Check Existing Vote
        Vote existingVote =
                voteRepository
                        .findByUserIdAndComplaintId(
                                user.getId(),
                                complaintId
                        )
                        .orElse(null);

        VoteType newVoteType;

        try {

            newVoteType =
                    VoteType.valueOf(
                            voteType.toUpperCase()
                    );

        } catch (Exception e) {

            throw new BadRequestException(
                    "Invalid vote type"
            );
        }

        // If already voted same type
        if (existingVote != null
                && existingVote.getVoteType() == newVoteType) {

            throw new BadRequestException(
                    "You already voted this way"
            );
        }

        // Remove previous counts
        if (existingVote != null) {

            if (existingVote.getVoteType()
                    == VoteType.UPVOTE) {

                complaint.setUpvoteCount(
                        complaint.getUpvoteCount() - 1
                );

            } else {

                complaint.setDownvoteCount(
                        complaint.getDownvoteCount() - 1
                );
            }

            existingVote.setVoteType(newVoteType);

            voteRepository.save(existingVote);

        } else {

            Vote vote = Vote.builder()
                    .userId(user.getId())
                    .complaintId(complaintId)
                    .voteType(newVoteType)
                    .build();

            voteRepository.save(vote);
        }

        // Add New Count
        if (newVoteType == VoteType.UPVOTE) {

            complaint.setUpvoteCount(
                    complaint.getUpvoteCount() + 1
            );

        } else {

            complaint.setDownvoteCount(
                    complaint.getDownvoteCount() + 1
            );
        }

        complaintRepository.save(complaint);

        return "Vote recorded successfully";
    }
}