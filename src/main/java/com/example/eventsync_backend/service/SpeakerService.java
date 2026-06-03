package com.example.eventsync_backend.service;

import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<Speaker> getAllSpeakers() {
        return speakerRepository.findAll();
    }

    public Optional<Speaker> getSpeakerById(Long id) {
        return speakerRepository.findById(id);
    }

    public Speaker createSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    public Speaker updateSpeaker(Long id, Speaker newSpeaker) {

        return speakerRepository.findById(id)
                .map(speaker -> {

                    speaker.setFullName(newSpeaker.getFullName());
                    speaker.setBiography(newSpeaker.getBiography());
                    speaker.setProfilePicture(newSpeaker.getProfilePicture());
                    speaker.setWebsite(newSpeaker.getWebsite());
                    speaker.setLinkedin(newSpeaker.getLinkedin());
                    speaker.setGithub(newSpeaker.getGithub());

                    return speakerRepository.save(speaker);
                })
                .orElseThrow(() -> new RuntimeException("Speaker not found"));
    }

    public void deleteSpeaker(Long id) {
        speakerRepository.deleteById(id);
    }
}