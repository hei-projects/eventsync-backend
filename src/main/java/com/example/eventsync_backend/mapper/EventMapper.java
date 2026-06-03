package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.EventResponse;
import com.example.eventsync_backend.entity.Event;

public class EventMapper {

    public static EventResponse toResponse(Event event) {

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .location(event.getLocation())
                .build();
    }
}