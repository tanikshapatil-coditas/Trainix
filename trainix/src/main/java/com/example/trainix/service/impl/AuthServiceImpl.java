package com.example.trainix.service.impl;

import com.example.trainix.dto.AuthRequest;
import com.example.trainix.dto.AuthResponse;
import com.example.trainix.entity.Role;
import com.example.trainix.entity.Users;
import com.example.trainix.exception.InvalidUserException;
import com.example.trainix.repository.UserRepository;
import com.example.trainix.service.AuthService;
import com.example.trainix.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthResponse verify(AuthRequest request) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateToken(request.getEmail());
            Users users = userRepository.findByEmail(request.getEmail()).get();

            Set<String> roleNames = users.getRoleSet().stream().map(Role::getName)
                    .collect(Collectors.toSet());

            String name = userRepository.findByEmail(request.getEmail()).get().getFirstName().concat(" ").concat(userRepository.findByEmail(request.getEmail()).get().getLastName());
            return new AuthResponse(accessToken,roleNames,name);
        }
        else
            throw new InvalidUserException();
    }
}