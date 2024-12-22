package com.example.trainix.mapper;

import com.example.trainix.dto.GetAllUsersResponse;
import com.example.trainix.entity.Role;
import com.example.trainix.entity.TechStack;
import com.example.trainix.entity.Users;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersMapper {
    public GetAllUsersResponse convertToDto(Users user){
        Set<String> roles = user.getRoleSet().stream().map(Role::getName).collect(Collectors.toSet());
        Set<String> techStack = user.getTechStackSet().stream().map(TechStack::getName).collect(Collectors.toSet());
        return new GetAllUsersResponse(
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getLocation(),roles,techStack);
    }
}
