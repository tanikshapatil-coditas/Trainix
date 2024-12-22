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
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> techStack;
    private Location location;
    private Boolean isAdmin;
    private String profilePic;
}
