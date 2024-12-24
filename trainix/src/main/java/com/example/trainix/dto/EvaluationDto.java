package com.example.trainix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto {
    private Long id;
    private Long courseId;
    private Long studentId;
    private Double logic;
    private Double grasping;
    private Double communication;
    private Double assignment;
    private Double problemSolving;
    private Double proactiveness;
    private Double rating;
    private String comments;
}
