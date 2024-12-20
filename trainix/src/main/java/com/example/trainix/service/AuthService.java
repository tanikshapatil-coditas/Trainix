package com.example.trainix.service;

import com.example.trainix.dto.AuthRequest;
import com.example.trainix.dto.AuthResponse;

public interface AuthService {
    AuthResponse verify(AuthRequest request);
}
