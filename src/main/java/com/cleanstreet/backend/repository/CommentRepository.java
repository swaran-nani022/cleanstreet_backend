package com.cleanstreet.backend.repository;

import com.cleanstreet.backend.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByComplaintId(String complaintId);
}