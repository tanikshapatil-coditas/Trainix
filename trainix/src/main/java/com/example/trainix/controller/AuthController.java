package com.example.trainix.controller;

import com.example.trainix.dto.ApiResponse;
import com.example.trainix.dto.AuthRequest;
import com.example.trainix.dto.AuthResponse;
import com.example.trainix.service.impl.AuthServiceImpl;
import com.example.trainix.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        com.example.trainix.dto.AuthResponse token = authService.verify(request);
        return ResponseUtil.success(token, "Logged In successfully!!");
    }
}
