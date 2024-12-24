package com.example.trainix.controller;

import com.example.trainix.dto.CourseResponseDto;
import com.example.trainix.dto.EvaluationDto;
import com.example.trainix.dto.GetAllUsersResponse;
import com.example.trainix.entity.Courses;
import com.example.trainix.entity.Evaluation;
import com.example.trainix.entity.Users;
import com.example.trainix.mapper.CoursesMapper;
import com.example.trainix.mapper.EvaluationMapper;
import com.example.trainix.mapper.UsersMapper;
import com.example.trainix.repository.CourseRepository;
import com.example.trainix.repository.EvaluationRepository;
import com.example.trainix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/active")
public class ActiveController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    CoursesMapper coursesMapper;

    @Autowired
    EvaluationMapper evaluationMapper;

//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<GetAllUsersResponse>> getAllActiveUsers() {
        List<Users> users = userRepository.findByIsDeletedFalse();
        List<GetAllUsersResponse> usersDtos = users.stream()
                .map(usersMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usersDtos);
    }

//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','TRAINER','STUDENT')")
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponseDto>> getAllActiveCourses() {
        List<Courses> courses = courseRepository.findByIsDeletedFalse();
        List<CourseResponseDto> coursesDtos = courses.stream()
                .map(coursesMapper::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(coursesDtos);
    }

    @GetMapping("/evaluations")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','TRAINER','STUDENT')")
    public ResponseEntity<List<EvaluationDto>> getAllActiveEvaluations() {
        List<EvaluationDto> evaluationDtos = evaluationRepository.findByIsDeletedFalse().stream()
                .sorted(Comparator.comparing(Evaluation::getCreatedAt).reversed())
                .map(evaluation -> evaluationMapper.toDto(evaluation,new EvaluationDto()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(evaluationDtos);
    }
}
