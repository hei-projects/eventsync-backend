package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.SessionRequest;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.NotFoundException;
import com.example.eventsync_backend.repository.EventRepository;
import com.example.eventsync_backend.repository.RoomRepository;
import com.example.eventsync_backend.repository.SessionRepository;
import com.example.eventsync_backend.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final SpeakerRepository speakerRepository;

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session getById(Integer id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found with id=" + id));
    }

    public List<Session> getSessionsByEvent(Integer eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id=" + eventId));
        return sessionRepository.findByEventId(eventId);
    }

    public List<Session> getLiveSessions(Instant now) {
        return sessionRepository.findLiveSessions(now);
    }

    public List<Session> getLiveSessionsByEvent(Integer eventId, Instant now) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id=" + eventId));
        return sessionRepository.findLiveByEventId(eventId, now);
    }

    public List<Session> getSessionsByRoom(Integer roomId) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id=" + roomId));
        return sessionRepository.findByRoomId(roomId);
    }

    @Transactional
    public Session create(SessionRequest request) {
        validateSessionRequest(request);
        Session session = new Session();
        session.setTitle(request.getTitle());
        session.setDescription(request.getDescription());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setRoomId(request.getRoomId());
        session.setCapacity(request.getCapacity());
        session.setEventId(request.getEventId());
        Session saved = sessionRepository.save(session);

        if (request.getSpeakerIds() != null) {
            for (Integer speakerId : request.getSpeakerIds()) {
                speakerRepository.findById(speakerId)
                        .orElseThrow(() -> new NotFoundException("Speaker not found with id=" + speakerId));
                sessionRepository.addSpeakerToSession(saved.getId(), speakerId);
            }
        }
        return saved;
    }

    @Transactional
    public Session update(Integer id, SessionRequest request) {
        getById(id);
        validateSessionRequest(request);
        Session session = new Session();
        session.setId(id);
        session.setTitle(request.getTitle());
        session.setDescription(request.getDescription());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setRoomId(request.getRoomId());
        session.setCapacity(request.getCapacity());
        session.setEventId(request.getEventId());

        sessionRepository.deleteAllSpeakersFromSession(id);
        if (request.getSpeakerIds() != null) {
            for (Integer speakerId : request.getSpeakerIds()) {
                speakerRepository.findById(speakerId)
                        .orElseThrow(() -> new NotFoundException("Speaker not found with id=" + speakerId));
                sessionRepository.addSpeakerToSession(id, speakerId);
            }
        }
        return sessionRepository.save(session);
    }

    @Transactional
    public void delete(Integer id) {
        getById(id);
        sessionRepository.deleteById(id);
    }

    public List<Speaker> getSpeakersForSession(Integer sessionId) {
        getById(sessionId);
        return speakerRepository.findBySessionId(sessionId);
    }

    private void validateSessionRequest(SessionRequest req) {
        if (!StringUtils.hasText(req.getTitle())) {
            throw new BadRequestException("Session title is required");
        }
        if (req.getStartTime() == null || req.getEndTime() == null) {
            throw new BadRequestException("Start time and end time are required");
        }
        if (req.getStartTime().isAfter(req.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }
        if (req.getRoomId() == null) {
            throw new BadRequestException("Room ID is required");
        }
        if (req.getEventId() == null) {
            throw new BadRequestException("Event ID is required");
        }


        if (eventRepository.findById(req.getEventId()).isEmpty()) {
            throw new NotFoundException("Associated event not found with id=" + req.getEventId());
        }
        if (roomRepository.findById(req.getRoomId()).isEmpty()) {
            throw new NotFoundException("Associated room not found with id=" + req.getRoomId());
        }
    }
}
