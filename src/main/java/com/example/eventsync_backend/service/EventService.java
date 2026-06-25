package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.CreateEventRequest;
import com.example.eventsync_backend.entity.Event;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    public Event createEvent(CreateEventRequest request) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .location(request.getLocation())
                .longDescription(request.getLongDescription())
                .venue(request.getVenue())
                .coverImage(request.getCoverImage())
                .tags(request.getTags())
                .build();
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, CreateEventRequest request) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(request.getTitle());
                    event.setDescription(request.getDescription());
                    event.setStartDate(request.getStartDate());
                    event.setEndDate(request.getEndDate());
                    event.setLocation(request.getLocation());
                    event.setLongDescription(request.getLongDescription());
                    event.setVenue(request.getVenue());
                    event.setCoverImage(request.getCoverImage());
                    event.setTags(request.getTags());
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}