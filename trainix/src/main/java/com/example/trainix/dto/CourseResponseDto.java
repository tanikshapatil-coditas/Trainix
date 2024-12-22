package com.example.trainix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseName;
    private Long trainerId;
    private String trainerName;
    private String location;
    private Set<Long> stakeholderIds;
    private String status;
    private String description;
    private List<String> students;
}
