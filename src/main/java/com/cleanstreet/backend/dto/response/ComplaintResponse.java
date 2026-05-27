package com.cleanstreet.backend.dto.response;

import com.cleanstreet.backend.enums.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponse {

    private String id;

    private String title;

    private String description;

    private String category;

    private String photo;

    private String address;

    private ComplaintStatus status;

    private Integer upvoteCount;

    private Integer downvoteCount;

    private LocalDateTime createdAt;
}