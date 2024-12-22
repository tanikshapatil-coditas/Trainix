package com.example.trainix.repository;

import com.example.trainix.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findByStudentIdAndIsDeletedFalse(Long studentId);

    List<Evaluation> findByStudentId(Long studentId);

    List<Evaluation> findByIsDeletedFalse();

}
