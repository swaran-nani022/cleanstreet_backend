package com.cleanstreet.backend.service;

import com.cleanstreet.backend.dto.request.ComplaintRequest;
import com.cleanstreet.backend.dto.response.ComplaintResponse;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComplaintService {

    ComplaintResponse createComplaint(
            ComplaintRequest request,
            MultipartFile file,
            String userEmail
    );

    List<ComplaintResponse> getAllComplaints();

    ComplaintResponse getComplaintById(String complaintId);
}