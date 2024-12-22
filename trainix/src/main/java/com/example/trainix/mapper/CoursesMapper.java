package com.example.trainix.mapper;

import com.example.trainix.dto.CourseDto;
import com.example.trainix.dto.CourseStatusUpdateDto;
import com.example.trainix.entity.Courses;
import com.example.trainix.entity.Stakeholder;
import com.example.trainix.entity.Users;
import com.example.trainix.enums.Status;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CoursesMapper {

    public CourseDto convertToDto(Courses course) {
        Set<Long> stakeholderIds = course.getStakeholderSet().stream()
                .map(Stakeholder::getId)
                .collect(Collectors.toSet());

        return new CourseDto(
                course.getCourseName(),
                course.getTrainer().getId(),
                course.getCourseDescription(),
                course.getLocation(),
                course.getStatus(),
                stakeholderIds
        );
    }

    public Courses convertToEntity(CourseDto courseDto, Users trainer, Set<Stakeholder> stakeholders) {
        Courses course = new Courses();
        course.setCourseName(courseDto.getCourseName());
        course.setTrainer(trainer);
        course.setCourseDescription(courseDto.getCourseDescription());
        course.setLocation(courseDto.getLocation());
        course.setStatus(courseDto.getStatus());
        course.setStakeholderSet(stakeholders);
        return course;
    }

    public void updateEntityFromDto(CourseDto courseDto, Courses course, Users trainer, Set<Stakeholder> stakeholders) {
        course.setCourseName(courseDto.getCourseName());
        course.setTrainer(trainer);
        course.setCourseDescription(courseDto.getCourseDescription());
        course.setLocation(courseDto.getLocation());
        course.setStatus(courseDto.getStatus());
        course.setStakeholderSet(stakeholders);
    }

    public void patchStatus(CourseStatusUpdateDto courseStatusUpdateDto){
        Courses course = new Courses();
        course.setStatus(courseStatusUpdateDto.getStatus());
    }
}
