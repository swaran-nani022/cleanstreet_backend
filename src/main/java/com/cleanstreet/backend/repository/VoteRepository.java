package com.cleanstreet.backend.repository;

import com.cleanstreet.backend.entity.Vote;
import com.cleanstreet.backend.enums.VoteType;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {

    Optional<Vote> findByUserIdAndComplaintId(String userId, String complaintId);

    long countByComplaintIdAndVoteType(String complaintId, VoteType voteType);
}