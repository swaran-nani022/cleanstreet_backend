package com.cleanstreet.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cleanstreet.backend.dto.request.LoginRequest;
import com.cleanstreet.backend.dto.request.RegisterRequest;
import com.cleanstreet.backend.dto.response.AuthResponse;
import com.cleanstreet.backend.entity.User;
import com.cleanstreet.backend.enums.Role;
import com.cleanstreet.backend.exception.BadRequestException;
import com.cleanstreet.backend.exception.ResourceNotFoundException;
import com.cleanstreet.backend.repository.UserRepository;
import com.cleanstreet.backend.security.JwtService;
import com.cleanstreet.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // Check Existing Email
        if (userRepository.existsByEmail(request.getEmail())) {

            throw new BadRequestException(
                    "Email already registered"
            );
        }

        // Create User
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .location(request.getLocation())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .build();

        // Save User
        userRepository.save(user);

        // Generate JWT
        String token =
                jwtService.generateToken(user.getEmail());

        // Return Response
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // Find User
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        // Verify Password
        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new BadRequestException(
                    "Invalid credentials"
            );
        }

        // Generate JWT
        String token =
                jwtService.generateToken(user.getEmail());

        // Return Response
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}