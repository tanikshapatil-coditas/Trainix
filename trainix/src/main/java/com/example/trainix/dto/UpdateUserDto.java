package com.example.trainix.dto;

import com.example.trainix.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
    private Set<String> techStacks;
    private Location location;
    private String profilePic;
}
