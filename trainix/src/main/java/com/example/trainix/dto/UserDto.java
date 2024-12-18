package com.example.trainix.dto;

import com.example.trainix.enums.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Location location;
    private String profilePic;
}
