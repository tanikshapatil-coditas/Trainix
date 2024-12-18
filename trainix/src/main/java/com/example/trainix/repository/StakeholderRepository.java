package com.example.trainix.repository;

import com.example.trainix.entity.Stakeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderRepository extends JpaRepository<Stakeholder,Long> {
}
