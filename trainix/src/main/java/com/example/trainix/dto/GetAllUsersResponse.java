package com.example.trainix.dto;

import com.example.trainix.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsersResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Location location;
    private Set<String> roles;
    private Set<String> techStack;
}
