package com.cleanstreet.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cleanstreet.backend.dto.response.ComplaintResponse;
import com.cleanstreet.backend.entity.Complaint;
import com.cleanstreet.backend.entity.User;
import com.cleanstreet.backend.enums.ComplaintStatus;
import com.cleanstreet.backend.exception.BadRequestException;
import com.cleanstreet.backend.exception.ResourceNotFoundException;
import com.cleanstreet.backend.repository.ComplaintRepository;
import com.cleanstreet.backend.repository.UserRepository;
import com.cleanstreet.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ComplaintRepository complaintRepository;

    private final UserRepository userRepository;

    @Override
    public ComplaintResponse updateComplaintStatus(
            String complaintId,
            String status
    ) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found"
                                )
                        );

        ComplaintStatus complaintStatus;

        try {

            complaintStatus =
                    ComplaintStatus.valueOf(
                            status.toUpperCase()
                    );

        } catch (Exception e) {

            throw new BadRequestException(
                    "Invalid complaint status"
            );
        }

        complaint.setStatus(complaintStatus);

        Complaint updatedComplaint =
                complaintRepository.save(complaint);

        return mapToResponse(updatedComplaint);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public String deleteComplaint(String complaintId) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found"
                                )
                        );

        complaintRepository.delete(complaint);

        return "Complaint deleted successfully";
    }

    // DTO Mapping
    private ComplaintResponse mapToResponse(
            Complaint complaint
    ) {

        return ComplaintResponse.builder()
                .id(complaint.getId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .photo(complaint.getPhoto())
                .address(complaint.getAddress())
                .status(complaint.getStatus())
                .upvoteCount(complaint.getUpvoteCount())
                .downvoteCount(complaint.getDownvoteCount())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}