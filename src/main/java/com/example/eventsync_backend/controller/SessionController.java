
package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateSessionRequest;
import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.dto.SpeakerResponse;
import com.example.eventsync_backend.mapper.SessionMapper;
import com.example.eventsync_backend.mapper.SpeakerMapper;
import com.example.eventsync_backend.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<SessionResponse> getAllSessions() {
        return sessionService.getAllSessions()
                .stream()
                .map(SessionMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public SessionResponse getSessionById(@PathVariable Long id) {
        return SessionMapper.toResponse(sessionService.getSessionById(id));
    }

    @PostMapping
    public SessionResponse createSession(@Valid @RequestBody CreateSessionRequest request) {
        return SessionMapper.toResponse(sessionService.createSession(request));
    }

    @PutMapping("/{id}")
    public SessionResponse updateSession(@PathVariable Long id,
                                         @Valid @RequestBody CreateSessionRequest request) {
        return SessionMapper.toResponse(sessionService.updateSession(id, request));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return Map.of("id", id);
    }

    @GetMapping("/{id}/live")
    public boolean isSessionLive(@PathVariable Long id) {
        return sessionService.isSessionLive(sessionService.getSessionById(id));
    }

    @GetMapping("/{id}/speakers")
    public List<SpeakerResponse> getSessionSpeakers(@PathVariable Long id) {
        return sessionService.getSpeakersBySession(id)
                .stream()
                .map(SpeakerMapper::toResponse)
                .toList();
    }
}


