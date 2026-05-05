package com.example.eventsync_backend.service;


import com.example.eventsync_backend.dto.EventRequest;
import com.example.eventsync_backend.entity.Event;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.NotFoundException;
import com.example.eventsync_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event getById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id=" + id));
    }

    @Transactional
    public Event create(EventRequest request) {
        validateEventRequest(request);
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setLocation(request.getLocation());
        return eventRepository.save(event);
    }

    @Transactional
    public Event update(Integer id, EventRequest request) {
        getById(id);
        validateEventRequest(request);
        Event event = new Event();
        event.setId(id);
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setLocation(request.getLocation());
        return eventRepository.save(event);
    }

    @Transactional
    public void delete(Integer id) {
        getById(id);
        eventRepository.deleteById(id);
    }

    private void validateEventRequest(EventRequest req) {
        if (!StringUtils.hasText(req.getTitle())) {
            throw new BadRequestException("Event title is required");
        }
        if (req.getStartDate() == null || req.getEndDate() == null) {
            throw new BadRequestException("Start date and end date are required");
        }
        if (req.getStartDate().isAfter(req.getEndDate())) {
            throw new BadRequestException("Start date must be before end date");
        }
    }
}