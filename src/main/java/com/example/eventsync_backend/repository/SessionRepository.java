package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository
        extends JpaRepository<Session, Long> {

    List<Session> findByEventIdOrderByStartTimeAsc(Long eventId);

    List<Session> findByRoomIdOrderByStartTimeAsc(Long roomId);
}