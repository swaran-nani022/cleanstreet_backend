package com.cleanstreet.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.cleanstreet.backend.dto.request.ComplaintRequest;
import com.cleanstreet.backend.dto.response.ComplaintResponse;

import com.cleanstreet.backend.service.ComplaintService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // Create Complaint
    @PostMapping(
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ComplaintResponse>
    createComplaint(

            @RequestPart("data")
            String data,

            @RequestPart(
                    value = "file",
                    required = false
            )
            MultipartFile file,

            Authentication authentication
    ) {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();

            ComplaintRequest request =
                    mapper.readValue(
                            data,
                            ComplaintRequest.class
                    );

            String userEmail =
                    authentication.getName();

            ComplaintResponse response =
                    complaintService.createComplaint(
                            request,
                            file,
                            userEmail
                    );

            return new ResponseEntity<>(
                    response,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid request data"
            );
        }
    }

    // Get All Complaints
    @GetMapping
    public ResponseEntity<List<ComplaintResponse>>
    getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }

    // Get Complaint By ID
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse>
    getComplaintById(

            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                complaintService.getComplaintById(id)
        );
    }
}