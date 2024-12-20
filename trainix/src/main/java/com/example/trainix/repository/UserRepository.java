package com.example.trainix.repository;

import com.example.trainix.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);

    @Query("SELECT u FROM Users u JOIN u.roleSet r WHERE " +
            "u.isDeleted = false AND " +
            "(:name IS NULL OR u.firstName LIKE CONCAT(:name, '%') OR u.lastName LIKE CONCAT(:name, '%')) AND " +
            "(:role IS NULL OR r.name = :role)")
    Page<Users> findAllByNameAndRole(@Param("name") String name, @Param("role") String role, Pageable pageable);


}
