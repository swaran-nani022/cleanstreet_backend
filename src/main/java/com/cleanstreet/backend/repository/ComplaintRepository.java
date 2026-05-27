package com.cleanstreet.backend.repository;

import com.cleanstreet.backend.entity.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {

    List<Complaint> findByUserId(String userId);
}