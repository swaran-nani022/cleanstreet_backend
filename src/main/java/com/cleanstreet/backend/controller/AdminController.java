package com.cleanstreet.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleanstreet.backend.dto.response.ComplaintResponse;
import com.cleanstreet.backend.entity.User;
import com.cleanstreet.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // Update Complaint Status
    @PutMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponse>
    updateComplaintStatus(

            @PathVariable String complaintId,

            @RequestParam String status
    ) {

        return ResponseEntity.ok(
                adminService.updateComplaintStatus(
                        complaintId,
                        status
                )
        );
    }

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>>
    getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    // Delete Complaint
    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<?> deleteComplaint(

            @PathVariable String complaintId
    ) {

        String response =
                adminService.deleteComplaint(
                        complaintId
                );

        return ResponseEntity.ok(
                Map.of("message", response)
        );
    }
}