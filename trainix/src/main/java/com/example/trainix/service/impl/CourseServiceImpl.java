package com.example.trainix.service.impl;

import com.example.trainix.dto.AddStudentsDto;
import com.example.trainix.dto.CourseDto;
import com.example.trainix.dto.CourseResponseDto;
import com.example.trainix.dto.CourseStatusUpdateDto;
import com.example.trainix.entity.Courses;
import com.example.trainix.entity.Role;
import com.example.trainix.entity.Stakeholder;
import com.example.trainix.entity.Users;
import com.example.trainix.mapper.CoursesMapper;
import com.example.trainix.repository.CourseRepository;
import com.example.trainix.repository.RoleRepository;
import com.example.trainix.repository.StakeholderRepository;
import com.example.trainix.repository.UserRepository;
import com.example.trainix.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private RoleRepository roleRepository;

    //1.Create course
    @Override
    public Courses createCourse(CourseDto courseDto) {
        Users trainer = userRepository.findById(courseDto.getTrainerId())
                .orElseThrow(() -> new RuntimeException("Trainer not found!!"));

        addRoleIfNotPresent(trainer, roleRepository.findByName("TRAINER").orElseThrow(() -> new RuntimeException("Role not found")));

        Set<Stakeholder> stakeholderSet = new HashSet<>();
        for (Long stakeholderId : courseDto.getStakeholderIds()) {
            Stakeholder stakeholder = stakeholderRepository.findById(stakeholderId)
                    .orElseThrow(() -> new RuntimeException("Stakeholder not found!!"));
            stakeholderSet.add(stakeholder);
        }

        Courses course = coursesMapper.convertToEntity(courseDto, trainer, stakeholderSet);
        return courseRepository.save(course);
    }

    private void addRoleIfNotPresent(Users user, Role role) {
        boolean hasRole = user.getRoleSet().stream().anyMatch(role1 -> role1.getName() == role.getName());
        if (!hasRole) {
            Role role1 = roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role not found!!"));
            user.getRoleSet().add(role1);
            userRepository.save(user);
        }


    }


    //2.Update course
    @Override
    public Courses updateCourse(CourseDto courseDto, Long id) {
        Courses existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!!"));

        if (existingCourse.getIsDeleted()) {
            throw new RuntimeException("Course has been deleted and cannot be updated.");
        }

        Users trainer = userRepository.findById(courseDto.getTrainerId())
                .orElseThrow(() -> new RuntimeException("Trainer not found!!"));

        Set<Stakeholder> stakeholderSet = new HashSet<>();
        for (Long stakeholderId : courseDto.getStakeholderIds()) {
            Stakeholder stakeholder = stakeholderRepository.findById(stakeholderId)
                    .orElseThrow(() -> new RuntimeException("Stakeholder not found!!"));
            stakeholderSet.add(stakeholder);
        }
        coursesMapper.updateEntityFromDto(courseDto, existingCourse, trainer, stakeholderSet);
        return courseRepository.save(existingCourse);
    }

    //3.Delete
    @Override
    public void deleteCourse(Long id) {
        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!!"));
        if (course.getIsDeleted()) {
            throw new RuntimeException("Course has been deleted and cannot be updated!!");
        }
        course.setIsDeleted(true);
        courseRepository.save(course);
    }

    //4.Set course status(Patch)
    @Override
    public void updateStatus(CourseStatusUpdateDto courseStatusUpdateDto, Long id) {
        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!!"));
        if (course.getIsDeleted()) {
            throw new RuntimeException("Course has been deleted and status cannot be updated!!");
        }
        course.setStatus(courseStatusUpdateDto.getStatus());
        courseRepository.save(course);
    }

    //5.Add students to course
    @Override
    public void addStudentsToCourse(Long courseId, AddStudentsDto addStudentsDto) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getIsDeleted()) {
            throw new RuntimeException("Course has been deleted and status cannot be updated!!");
        }
        List<Users> students = userRepository.findAllById(addStudentsDto.getStudentIds());
        if (students.size() != addStudentsDto.getStudentIds().size()) {
            throw new RuntimeException("One or more students not found");
        }
        for (Users student : students) {
            if (!student.getCoursesSet().contains(course)) {
                student.getCoursesSet().add(course);
            }
        }
        userRepository.saveAll(students);
    }

    //6.Search courses API
    @Override
    public Page<CourseResponseDto> searchCourses(String courseName, String trainerName, String stakeholderName, Pageable pageable) {
        Page<Courses> coursesPage;
        if (courseName != null) {
            coursesPage = courseRepository.findByCourseName(courseName, pageable);
        } else if (trainerName != null) {
            coursesPage = courseRepository.findCoursesByTrainerName(trainerName, pageable);
        } else if (stakeholderName != null) {
            coursesPage = courseRepository.findCoursesByStakeholderName(stakeholderName, pageable);
        } else {
            coursesPage = courseRepository.findAll(pageable);
        }
        return coursesPage.map(coursesMapper::convertToResponseDto);
    }
}