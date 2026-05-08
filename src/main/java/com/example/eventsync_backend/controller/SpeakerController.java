package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.SpeakerRequest;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speakers")
@RequiredArgsConstructor
public class SpeakerController {
    private final SpeakerService speakerService;

    @GetMapping
    public ResponseEntity<List<Speaker>> getAll() {
        return ResponseEntity.ok(speakerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Speaker> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(speakerService.getById(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Speaker>> getBySession(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(speakerService.getSpeakersBySession(sessionId));
    }

    @PostMapping
    public ResponseEntity<Speaker> create(@RequestBody SpeakerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(speakerService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Speaker> update(@PathVariable Integer id, @RequestBody SpeakerRequest request) {
        return ResponseEntity.ok(speakerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        speakerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
