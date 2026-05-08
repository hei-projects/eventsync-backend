package com.example.eventsync_backend.controller;


import com.example.eventsync_backend.dto.SessionRequest;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<Session>> getAll() {
        return ResponseEntity.ok(sessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(sessionService.getById(id));
    }

    @GetMapping("/{id}/speakers")
    public ResponseEntity<List<Speaker>> getSpeakers(@PathVariable Integer id) {
        return ResponseEntity.ok(sessionService.getSpeakersForSession(id));
    }

    @GetMapping("/live")
    public ResponseEntity<List<Session>> getLiveSessions(@RequestParam(required = false) Instant now) {
        Instant instant = (now != null) ? now : Instant.now();
        return ResponseEntity.ok(sessionService.getLiveSessions(instant));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Session>> getByEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(sessionService.getSessionsByEvent(eventId));
    }

    @GetMapping("/event/{eventId}/live")
    public ResponseEntity<List<Session>> getLiveByEvent(@PathVariable Integer eventId,
                                                        @RequestParam(required = false) Instant now) {
        Instant instant = (now != null) ? now : Instant.now();
        return ResponseEntity.ok(sessionService.getLiveSessionsByEvent(eventId, instant));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Session>> getByRoom(@PathVariable Integer roomId) {
        return ResponseEntity.ok(sessionService.getSessionsByRoom(roomId));
    }

    @PostMapping
    public ResponseEntity<Session> create(@RequestBody SessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> update(@PathVariable Integer id, @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}