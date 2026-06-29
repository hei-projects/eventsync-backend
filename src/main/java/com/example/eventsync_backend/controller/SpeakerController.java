


package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateSpeakerRequest;
import com.example.eventsync_backend.dto.SpeakerResponse;
import com.example.eventsync_backend.mapper.SpeakerMapper;
import com.example.eventsync_backend.service.SpeakerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public List<SpeakerResponse> getAllSpeakers() {
        return speakerService.getAllSpeakers()
                .stream()
                .map(SpeakerMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public SpeakerResponse getSpeakerById(@PathVariable Long id) {
        return SpeakerMapper.toResponse(speakerService.getSpeakerById(id));
    }

    @PostMapping
    public SpeakerResponse createSpeaker(@Valid @RequestBody CreateSpeakerRequest request) {
        return SpeakerMapper.toResponse(speakerService.createSpeaker(request));
    }

    @PutMapping("/{id}")
    public SpeakerResponse updateSpeaker(@PathVariable Long id,
                                         @Valid @RequestBody CreateSpeakerRequest request) {
        return SpeakerMapper.toResponse(speakerService.updateSpeaker(id, request));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> deleteSpeaker(@PathVariable Long id) {
        speakerService.deleteSpeaker(id);
        return Map.of("id", id);
    }
}



