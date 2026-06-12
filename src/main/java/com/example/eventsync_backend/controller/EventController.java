package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateEventRequest;
import com.example.eventsync_backend.dto.EventResponse;
import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.mapper.EventMapper;
import com.example.eventsync_backend.mapper.SessionMapper;
import com.example.eventsync_backend.service.EventService;
import com.example.eventsync_backend.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final SessionService sessionService;

    public EventController(EventService eventService, SessionService sessionService) {
        this.eventService = eventService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents()
                .stream()
                .map(EventMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        return EventMapper.toResponse(eventService.getEventById(id));
    }

    @PostMapping
    public EventResponse createEvent(@Valid @RequestBody CreateEventRequest request) {
        return EventMapper.toResponse(eventService.createEvent(request));
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(@PathVariable Long id,
                                     @Valid @RequestBody CreateEventRequest request) {
        return EventMapper.toResponse(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/{eventId}/schedule")
    public List<SessionResponse> getEventSchedule(@PathVariable Long eventId) {
        return sessionService.getSessionsByEvent(eventId)
                .stream()
                .map(SessionMapper::toResponse)
                .toList();
    }

    @GetMapping("/{eventId}/live-sessions")
    public List<SessionResponse> getLiveSessions(@PathVariable Long eventId) {
        return sessionService.getLiveSessionsByEvent(eventId)
                .stream()
                .map(SessionMapper::toResponse)
                .toList();
    }
}
