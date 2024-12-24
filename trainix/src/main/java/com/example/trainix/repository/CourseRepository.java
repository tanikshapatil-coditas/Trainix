package com.example.trainix.repository;

import com.example.trainix.entity.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses,Long> {

    @Query(value = "SELECT * FROM courses WHERE is_deleted = false", nativeQuery = true)
    List<Courses> findByIsDeletedFalse();

    @Query(value = "SELECT * FROM courses WHERE course_name LIKE %:courseName%", nativeQuery = true)
    Page<Courses> findByCourseName(@Param("courseName") String courseName, Pageable pageable);

    @Query(value = "SELECT c.* FROM courses c JOIN users u ON c.trainer_id = u.id WHERE u.first_name || ' ' || u.last_name LIKE %:trainerName%", nativeQuery = true)
    Page<Courses> findCoursesByTrainerName(@Param("trainerName") String trainerName,Pageable pageable);

    @Query(value = "SELECT c.* " +
            "FROM courses c " +
            "JOIN courses_stakeholder cs ON c.id = cs.courses_id " +
            "JOIN stakeholder s ON cs.stakeholder_id = s.id " +
            "JOIN users u ON s.id = u.id " +
            "WHERE u.first_name || ' ' || u.last_name ILIKE %:stakeholderName%",
            nativeQuery = true)
    Page<Courses> findCoursesByStakeholderName(@Param("stakeholderName") String stakeholderName, Pageable pageable);
}
