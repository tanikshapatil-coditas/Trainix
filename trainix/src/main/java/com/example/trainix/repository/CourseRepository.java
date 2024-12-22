package com.example.trainix.repository;

import com.example.trainix.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses,Long> {
    List<Courses> findByIsDeletedFalse();
}
