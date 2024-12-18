package com.example.trainix.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity {



    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    Timestamp createdAt;

    @PrePersist
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
    }
}
