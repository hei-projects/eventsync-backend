package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.EventResponse;
import com.example.eventsync_backend.entity.Event;

import java.time.LocalDateTime;

public class EventMapper {

    public static EventResponse toResponse(Event event) {
        LocalDateTime now = LocalDateTime.now();
        boolean isLive = now.isAfter(event.getStartDate()) && now.isBefore(event.getEndDate());
        String status = now.isBefore(event.getStartDate()) ? "upcoming"
                : now.isAfter(event.getEndDate()) ? "past"
                : "live";

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .location(event.getLocation())
                .longDescription(event.getLongDescription())
                .venue(event.getVenue())
                .coverImage(event.getCoverImage())
                .tags(event.getTags())
                .live(isLive)
                .status(status)
                .build();
    }
}