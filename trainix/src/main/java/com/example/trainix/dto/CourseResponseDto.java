package com.example.trainix.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseName;
    private Long trainerId;
    private String trainerName;
    private String location;
    private Set<String> stakeholders;
    private String status;
    private String description;

    public CourseResponseDto(Long id, String courseName, Long trainerId, String trainerName, String location, Set<String> stakeholders, String status, String description) {
        this.id = id;
        this.courseName = courseName;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.location = location;
        this.stakeholders = stakeholders;
        this.status = status;
        this.description = description;
    }
}
