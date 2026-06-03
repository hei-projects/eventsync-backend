package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.service.SessionService;
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
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }

    @PutMapping("/{id}")
    public Session updateSession(@PathVariable Long id,
                                 @RequestBody Session session) {

        return sessionService.updateSession(id, session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }

    @GetMapping("/{id}/live")
    public boolean isSessionLive(@PathVariable Long id) {

        Session session = sessionService.getSessionById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        return sessionService.isSessionLive(session);
    }
}