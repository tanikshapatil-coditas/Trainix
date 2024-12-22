package com.example.trainix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Boolean isEditable;
    private Boolean isDeletable;
}
