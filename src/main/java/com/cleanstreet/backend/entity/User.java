package com.cleanstreet.backend.entity;

import com.cleanstreet.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String location;

    @Builder.Default
    private Role role = Role.USER;

    private String profilePhoto;

    private String photoPublicId;

    // Registration OTP
    private String emailVerificationOtp;

    // Forgot Password OTP
    private String passwordResetOtp;

    private LocalDateTime otpExpiry;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}