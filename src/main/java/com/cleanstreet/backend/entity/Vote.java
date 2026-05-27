package com.cleanstreet.backend.entity;

import com.cleanstreet.backend.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "votes")
public class Vote {

    @Id
    private String id;

    private String userId;

    private String complaintId;

    private VoteType voteType;
}