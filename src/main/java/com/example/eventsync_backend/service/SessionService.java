package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.CreateSessionRequest;
import com.example.eventsync_backend.entity.Event;
import com.example.eventsync_backend.entity.Room;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.EventRepository;
import com.example.eventsync_backend.repository.RoomRepository;
import com.example.eventsync_backend.repository.SessionRepository;
import com.example.eventsync_backend.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final SpeakerRepository speakerRepository;

    public SessionService(SessionRepository sessionRepository,
                          EventRepository eventRepository,
                          RoomRepository roomRepository,
                          SpeakerRepository speakerRepository) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
        this.speakerRepository = speakerRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
    }

    public Session createSession(CreateSessionRequest request) {
        if (request.getSpeakerIds() == null || request.getSpeakerIds().isEmpty()) {
            throw new BadRequestException("Session must have at least one speaker");
        }

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + request.getEventId()));

        List<Speaker> speakers = speakerRepository.findAllById(request.getSpeakerIds());
        if (speakers.size() != request.getSpeakerIds().size()) {
            throw new ResourceNotFoundException("One or more speakers not found");
        }

        Room room = null;
        if (request.getRoomId() != null) {
            room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));
        }

        Session session = Session.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .capacity(request.getCapacity())
                .event(event)
                .room(room)
                .speakers(speakers)
                .track(request.getTrack())
                .level(request.getLevel())
                .tags(request.getTags())
                .build();

        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, CreateSessionRequest request) {
        if (request.getSpeakerIds() == null || request.getSpeakerIds().isEmpty()) {
            throw new BadRequestException("Session must have at least one speaker");
        }

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + request.getEventId()));

        List<Speaker> speakers = speakerRepository.findAllById(request.getSpeakerIds());
        if (speakers.size() != request.getSpeakerIds().size()) {
            throw new ResourceNotFoundException("One or more speakers not found");
        }

        Room room;
        if (request.getRoomId() != null) {
            room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));
        } else {
            room = null;
        }

        return sessionRepository.findById(id)
                .map(session -> {
                    session.setTitle(request.getTitle());
                    session.setDescription(request.getDescription());
                    session.setStartTime(request.getStartTime());
                    session.setEndTime(request.getEndTime());
                    session.setCapacity(request.getCapacity());
                    session.setEvent(event);
                    session.setRoom(room);
                    session.setSpeakers(speakers);
                    session.setTrack(request.getTrack());
                    session.setLevel(request.getLevel());
                    session.setTags(request.getTags());
                    return sessionRepository.save(session);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
    }

    public void deleteSession(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session not found with id: " + id);
        }
        sessionRepository.deleteById(id);
    }

    public boolean isSessionLive(Session session) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(session.getStartTime())
                && now.isBefore(session.getEndTime());
    }

    public List<Session> getSessionsByEvent(Long eventId) {
        return sessionRepository.findByEventIdOrderByStartTimeAsc(eventId);
    }

    public List<Session> getLiveSessionsByEvent(Long eventId) {
        return sessionRepository.findByEventIdOrderByStartTimeAsc(eventId)
                .stream()
                .filter(this::isSessionLive)
                .toList();
    }

    public List<Session> getSessionsByRoom(Long roomId) {
        return sessionRepository.findByRoomIdOrderByStartTimeAsc(roomId);
    }

    public List<Speaker> getSpeakersBySession(Long sessionId) {
        Session session = getSessionById(sessionId);
        return session.getSpeakers();
    }
}
