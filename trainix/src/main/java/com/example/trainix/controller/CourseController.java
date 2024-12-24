package com.example.trainix.controller;

import com.example.trainix.dto.*;
import com.example.trainix.entity.Courses;
import com.example.trainix.enums.Status;
import com.example.trainix.service.impl.CourseServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<UserApiResponse> createCourse(@RequestBody CourseDto courseDto){
        Courses courses = courseService.createCourse(courseDto);
        return ResponseUtil.okay("Course created successfully!!", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<UserApiResponse> updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        Courses updatedCourse = courseService.updateCourse(courseDto, id);
        return ResponseUtil.updated("Course updated successfully!!", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<UserApiResponse> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseUtil.delete("Course deleted successfully!!", HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/status/{id}")
//    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<UserApiResponse> updateStatusCourse(@RequestParam("status") String status, @PathVariable Long id) {
        CourseStatusUpdateDto courseStatusUpdateDto = new CourseStatusUpdateDto(Status.valueOf(status.toUpperCase()));
        courseService.updateStatus(courseStatusUpdateDto, id);
        return ResponseUtil.updated("Course status updated successfully!!", HttpStatus.OK);
    }

    @PostMapping("/addStudents/{courseId}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<UserApiResponse> addStudentsToCourse(@PathVariable Long courseId, @RequestBody AddStudentsDto addStudentsDto) {
        courseService.addStudentsToCourse(courseId, addStudentsDto);
        return ResponseUtil.okay("Students added successfully!", HttpStatus.OK);
    }

    @GetMapping("/search")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','TRAINER')")
    public ResponseEntity<Page<CourseResponseDto>> searchCourses(@RequestParam(required = false) String courseName,
                                                                 @RequestParam(required = false) String trainerName,
                                                                 @RequestParam(required = false) String stakeholderName,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponseDto> coursesPage = courseService.searchCourses(courseName, trainerName, stakeholderName, pageable);
        return ResponseEntity.ok(coursesPage);
    }
}
