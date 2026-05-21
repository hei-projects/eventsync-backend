package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository
        extends JpaRepository<Room, Long> {
}
