package com.example.trainix.mapper;

import com.example.trainix.dto.EvaluationDto;
import com.example.trainix.entity.Evaluation;
import com.example.trainix.entity.Courses;
import com.example.trainix.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class EvaluationMapper {

    public EvaluationDto toDto(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }
        EvaluationDto dto = new EvaluationDto();
        dto.setId(evaluation.getId());
        dto.setLogic(evaluation.getLogic());
        dto.setGrasping(evaluation.getGrasping());
        dto.setCommunication(evaluation.getCommunication());
        dto.setAssignment(evaluation.getAssignment());
        dto.setProblemSolving(evaluation.getProblemSolving());
        dto.setProactiveness(evaluation.getProactiveness());
        dto.setRating(evaluation.getRating());
        dto.setComments(evaluation.getComments());
        dto.setCourseId(evaluation.getCourse().getId());
        dto.setStudentId(evaluation.getStudent().getId());
        return dto;
    }

    public Evaluation toEntity(EvaluationDto evaluationDto, Courses course, Users student) {
        if (evaluationDto == null || course == null || student == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setLogic(evaluationDto.getLogic());
        evaluation.setGrasping(evaluationDto.getGrasping());
        evaluation.setCommunication(evaluationDto.getCommunication());
        evaluation.setAssignment(evaluationDto.getAssignment());
        evaluation.setProblemSolving(evaluationDto.getProblemSolving());
        evaluation.setProactiveness(evaluationDto.getProactiveness());
        evaluation.setRating(evaluationDto.getRating());
        evaluation.setComments(evaluationDto.getComments());
        evaluation.setCourse(course);
        evaluation.setStudent(student);
        evaluation.setEvaluationDate(new java.util.Date());
        return evaluation;
    }
}
