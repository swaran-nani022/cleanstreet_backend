package com.cleanstreet.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cleanstreet.backend.dto.request.ComplaintRequest;
import com.cleanstreet.backend.dto.response.ComplaintResponse;

import com.cleanstreet.backend.entity.Complaint;
import com.cleanstreet.backend.entity.User;

import com.cleanstreet.backend.exception.ResourceNotFoundException;

import com.cleanstreet.backend.repository.ComplaintRepository;
import com.cleanstreet.backend.repository.UserRepository;

import com.cleanstreet.backend.service.CloudinaryService;
import com.cleanstreet.backend.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    private final UserRepository userRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    public ComplaintResponse createComplaint(
            ComplaintRequest request,
            MultipartFile file,
            String userEmail
    ) {

        // Find Logged In User
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        String imageUrl = null;

        String publicId = null;

        // Upload Image
        if (file != null && !file.isEmpty()) {

            try {

                Map uploadResult =
                        cloudinaryService.uploadFile(file);

                imageUrl =
                        uploadResult.get("secure_url")
                                .toString();

                publicId =
                        uploadResult.get("public_id")
                                .toString();

            } catch (Exception e) {

                throw new RuntimeException(
                        "Image upload failed"
                );
            }
        }

        // Create Complaint
        Complaint complaint = Complaint.builder()
                .userId(user.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .photo(imageUrl)
                .photoPublicId(publicId)
                .build();

        // Save Complaint
        Complaint savedComplaint =
                complaintRepository.save(complaint);

        // Return Response
        return mapToResponse(savedComplaint);
    }

    @Override
    public List<ComplaintResponse> getAllComplaints() {

        List<Complaint> complaints =
                complaintRepository.findAll();

        return complaints.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ComplaintResponse getComplaintById(
            String complaintId
    ) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found"
                                )
                        );

        return mapToResponse(complaint);
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