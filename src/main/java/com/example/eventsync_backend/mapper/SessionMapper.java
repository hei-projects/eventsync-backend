package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.entity.Session;

import java.time.LocalDateTime;

public class SessionMapper {

    public static SessionResponse toResponse(Session session) {

        LocalDateTime now = LocalDateTime.now();

        boolean live =
                now.isAfter(session.getStartTime())
                        && now.isBefore(session.getEndTime());

        return SessionResponse.builder()
                .id(session.getId())
                .title(session.getTitle())
                .description(session.getDescription())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .capacity(session.getCapacity())
                .roomName(session.getRoom().getName())
                .live(live)
                .build();
    }
}