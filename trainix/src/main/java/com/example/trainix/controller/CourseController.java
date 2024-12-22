package com.example.trainix.controller;

import com.example.trainix.dto.AddStudentsDto;
import com.example.trainix.dto.CourseDto;
import com.example.trainix.dto.CourseStatusUpdateDto;
import com.example.trainix.dto.UserApiResponse;
import com.example.trainix.entity.Courses;
import com.example.trainix.enums.Status;
import com.example.trainix.service.impl.CourseServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping("/create")
    public ResponseEntity<UserApiResponse> createCourse(@RequestBody CourseDto courseDto){
        Courses courses = courseService.createCourse(courseDto);
        return ResponseUtil.okay("Course created successfully!!", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserApiResponse> updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        Courses updatedCourse = courseService.updateCourse(courseDto, id);
        return ResponseUtil.updated("Course updated successfully!!", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserApiResponse> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseUtil.delete("Course deleted successfully!!", HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/status/{id}")
    public ResponseEntity<UserApiResponse> updateStatusCourse(@RequestParam("status") String status, @PathVariable Long id) {
        CourseStatusUpdateDto courseStatusUpdateDto = new CourseStatusUpdateDto(Status.valueOf(status.toUpperCase()));
        courseService.updateStatus(courseStatusUpdateDto, id);
        return ResponseUtil.updated("Course status updated successfully!!", HttpStatus.OK);
    }

    @PostMapping("/addStudents/{courseId}")
    public ResponseEntity<UserApiResponse> addStudentsToCourse(@PathVariable Long courseId, @RequestBody AddStudentsDto addStudentsDto) {
        courseService.addStudentsToCourse(courseId, addStudentsDto);
        return ResponseUtil.okay("Students added successfully!", HttpStatus.OK);
    }

}
