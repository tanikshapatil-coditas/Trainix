package com.example.trainix.dto;

import com.example.trainix.enums.Location;
import com.example.trainix.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private String courseName;
    private Long trainerId;
    private String courseDescription;
    private Location location;
    private Status status;
    private Set<Long> stakeholderIds;
    public CourseDto(Long id, String courseName, Long id1, String concat, String courseDescription, Location location, Status status, Set<Long> stakeholderIds) {
    }
}
