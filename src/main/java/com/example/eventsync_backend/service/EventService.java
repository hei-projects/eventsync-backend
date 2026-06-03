package com.example.eventsync_backend.service;

import com.example.eventsync_backend.entity.Event;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    public Event updateEvent(Long id, Event newEvent) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(newEvent.getTitle());
                    event.setDescription(newEvent.getDescription());
                    event.setStartDate(newEvent.getStartDate());
                    event.setEndDate(newEvent.getEndDate());
                    event.setLocation(newEvent.getLocation());
                    return eventRepository.save(event);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found"));
    }
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
