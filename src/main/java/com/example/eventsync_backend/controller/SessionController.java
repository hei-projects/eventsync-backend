
package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateSessionRequest;
import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.mapper.SessionMapper;
import com.example.eventsync_backend.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }

    @GetMapping("/{id}/live")
    public boolean isSessionLive(@PathVariable Long id) {
        return sessionService.isSessionLive(sessionService.getSessionById(id));
    }
}


