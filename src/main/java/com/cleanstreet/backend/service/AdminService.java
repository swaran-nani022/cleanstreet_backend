package com.cleanstreet.backend.service;

import java.util.List;

import com.cleanstreet.backend.dto.response.ComplaintResponse;
import com.cleanstreet.backend.entity.User;

public interface AdminService {

    ComplaintResponse updateComplaintStatus(
            String complaintId,
            String status
    );

    List<User> getAllUsers();

    String deleteComplaint(String complaintId);
}