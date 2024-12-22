package com.example.trainix.service.impl;

import com.example.trainix.dto.EvaluationDto;
import com.example.trainix.entity.Courses;
import com.example.trainix.entity.Evaluation;
import com.example.trainix.entity.Users;
import com.example.trainix.mapper.EvaluationMapper;
import com.example.trainix.repository.CourseRepository;
import com.example.trainix.repository.EvaluationRepository;
import com.example.trainix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    public void createEvaluation(EvaluationDto evaluationDto) {
        Courses course = courseRepository.findById(evaluationDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Users student = userRepository.findById(evaluationDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (course.getIsDeleted() || student.getIsDeleted()) {
            throw new RuntimeException("Course or student is deleted, cannot create evaluation.");
        }
        Evaluation evaluation = evaluationMapper.toEntity(evaluationDto, course, student);
        evaluationRepository.save(evaluation);
    }

    public List<EvaluationDto> getEvaluationsByUserId(Long userId) {
        List<Evaluation> evaluations = evaluationRepository.findByStudentIdAndIsDeletedFalse(userId);
        List<EvaluationDto> evaluationDtos = evaluations.stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toList());
        if (!evaluations.isEmpty()) {
            Evaluation latestEvaluation = evaluations.get(0);
            for (EvaluationDto dto : evaluationDtos) {
                if (dto.getId().equals(latestEvaluation.getId())) {
                    dto.setIsEditable(true);
                    dto.setIsDeletable(true);
                } else {
                    dto.setIsEditable(false);
                    dto.setIsDeletable(false);
                }
            }
        }
        return evaluationDtos;
    }

    public void updateEvaluation(Long id, EvaluationDto evaluationDto) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
        if (evaluation.getIsDeleted()) {
            throw new RuntimeException("Evaluation is deleted and cannot be updated.");
        }
        evaluation.setLogic(evaluationDto.getLogic());
        evaluation.setGrasping(evaluationDto.getGrasping());
        evaluation.setCommunication(evaluationDto.getCommunication());
        evaluation.setAssignment(evaluationDto.getAssignment());
        evaluation.setProblemSolving(evaluationDto.getProblemSolving());
        evaluation.setProactiveness(evaluationDto.getProactiveness());
        evaluation.setRating(evaluationDto.getRating());
        evaluation.setComments(evaluationDto.getComments());
        evaluationRepository.save(evaluation);
    }

    public void deleteEvaluation(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        if (evaluation.getIsDeleted()) {
            throw new RuntimeException("Evaluation is already deleted.");
        }
        evaluation.setIsDeleted(true);
        evaluationRepository.save(evaluation);
    }
}
