package com.example.trainix.security;

import com.example.trainix.entity.Role;
import com.example.trainix.entity.Users;
import com.example.trainix.exception.InvalidUserException;
import com.example.trainix.repository.RoleRepository;
import com.example.trainix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws InvalidUserException {
        Users user = userRepo.findByEmail(emailId)
                .orElseThrow(InvalidUserException::new);

        Set<Role> roles = new HashSet<>(user.getRoleSet());

        return new CustomUserDetails(user,roles);
    }
}
