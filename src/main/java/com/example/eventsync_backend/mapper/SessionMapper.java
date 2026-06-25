package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.entity.Speaker;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SessionMapper {

    public static SessionResponse toResponse(Session session) {
        LocalDateTime now = LocalDateTime.now();

        boolean live =
                now.isAfter(session.getStartTime())
                        && now.isBefore(session.getEndTime());

        List<String> speakerNames = session.getSpeakers() != null
                ? session.getSpeakers().stream()
                        .map(Speaker::getFullName)
                        .toList()
                : Collections.emptyList();

        List<Long> speakerIds = session.getSpeakers() != null
                ? session.getSpeakers().stream()
                        .map(Speaker::getId)
                        .toList()
                : Collections.emptyList();

        return SessionResponse.builder()
                .id(session.getId())
                .title(session.getTitle())
                .description(session.getDescription())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .capacity(session.getCapacity())
                .eventId(session.getEvent() != null ? session.getEvent().getId() : null)
                .roomName(session.getRoom() != null ? session.getRoom().getName() : null)
                .roomId(session.getRoom() != null ? session.getRoom().getId() : null)
                .speakerNames(speakerNames)
                .speakerIds(speakerIds)
                .live(live)
                .track(session.getTrack())
                .level(session.getLevel())
                .tags(session.getTags())
                .enrolled(session.getEnrolled())
                .build();
    }
}
