package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository
        extends JpaRepository<Speaker, Long> {
}
