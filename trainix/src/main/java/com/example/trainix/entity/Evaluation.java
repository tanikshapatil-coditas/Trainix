package com.example.trainix.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evaluations")
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logic", nullable = false)
    private Double logic;

    @Column(name = "grasping", nullable = false)
    private Double grasping;

    @Column(name = "communication", nullable = false)
    private Double communication;

    @Column(name = "assignment", nullable = false)
    private Double assignment;

    @Column(name = "problem_solving", nullable = false)
    private Double problemSolving;

    @Column(name = "proactiveness", nullable = false)
    private Double proactiveness;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "evaluation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date evaluationDate;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Users student;

}

