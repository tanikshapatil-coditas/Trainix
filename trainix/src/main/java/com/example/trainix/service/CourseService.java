package com.example.trainix.service;

import com.example.trainix.dto.AddStudentsDto;
import com.example.trainix.dto.CourseDto;
import com.example.trainix.dto.CourseStatusUpdateDto;
import com.example.trainix.entity.Courses;

import java.util.List;

public interface CourseService {
    Courses createCourse(CourseDto courseDto);

    Courses updateCourse(CourseDto courseDto,Long id);

     void deleteCourse(Long id);

     void updateStatus(CourseStatusUpdateDto courseStatusUpdateDto,Long id);

    void addStudentsToCourse(Long courseId, AddStudentsDto addStudentsDto);


}
