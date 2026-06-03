package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.service.SpeakerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public List<Speaker> getAllSpeakers() {
        return speakerService.getAllSpeakers();
    }

    @GetMapping("/{id}")
    public Speaker getSpeakerById(@PathVariable Long id) {
        return speakerService.getSpeakerById(id)
                .orElseThrow(() -> new RuntimeException("Speaker not found"));
    }

    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        return speakerService.createSpeaker(speaker);
    }

    @PutMapping("/{id}")
    public Speaker updateSpeaker(@PathVariable Long id,
                                 @RequestBody Speaker speaker) {

        return speakerService.updateSpeaker(id, speaker);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeaker(@PathVariable Long id) {
        speakerService.deleteSpeaker(id);
    }
}