package com.example.eventsync_backend.service;

import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }
    public Session updateSession(Long id, Session newSession) {

        return sessionRepository.findById(id)
                .map(session -> {

                    session.setTitle(newSession.getTitle());
                    session.setDescription(newSession.getDescription());
                    session.setStartTime(newSession.getStartTime());
                    session.setEndTime(newSession.getEndTime());
                    session.setCapacity(newSession.getCapacity());
                    session.setRoom(newSession.getRoom());
                    session.setEvent(newSession.getEvent());
                    session.setSpeakers(newSession.getSpeakers());

                    return sessionRepository.save(session);
                })
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public boolean isSessionLive(Session session) {

        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(session.getStartTime())
                && now.isBefore(session.getEndTime());
    }
}