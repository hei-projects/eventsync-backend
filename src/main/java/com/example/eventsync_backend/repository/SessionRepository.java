package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository
        extends JpaRepository<Session, Long> {
}