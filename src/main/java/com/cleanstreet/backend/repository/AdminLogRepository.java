package com.cleanstreet.backend.repository;

import com.cleanstreet.backend.entity.AdminLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminLogRepository extends MongoRepository<AdminLog, String> {
}