package com.cleanstreet.backend.service;

import com.cleanstreet.backend.dto.request.LoginRequest;
import com.cleanstreet.backend.dto.request.RegisterRequest;
import com.cleanstreet.backend.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}