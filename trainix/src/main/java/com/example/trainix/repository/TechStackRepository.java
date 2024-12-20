package com.example.trainix.repository;

import com.example.trainix.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack,Long> {
    @Query("SELECT t FROM TechStack t WHERE t.name = :name")
    Optional<TechStack> findByName(@Param("name") String name);
}
