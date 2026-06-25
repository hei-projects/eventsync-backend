
package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.CreateSpeakerRequest;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<Speaker> getAllSpeakers() {
        return speakerRepository.findAll();
    }

    public Speaker getSpeakerById(Long id) {
        return speakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found with id: " + id));
    }

    public Speaker createSpeaker(CreateSpeakerRequest request) {
        Speaker speaker = Speaker.builder()
                .fullName(request.getFullName())
                .biography(request.getBiography())
                .profilePicture(request.getProfilePicture())
                .website(request.getWebsite())
                .linkedin(request.getLinkedin())
                .github(request.getGithub())
                .title(request.getTitle())
                .company(request.getCompany())
                .twitter(request.getTwitter())
                .tags(request.getTags())
                .build();
        return speakerRepository.save(speaker);
    }

    public Speaker updateSpeaker(Long id, CreateSpeakerRequest request) {
        return speakerRepository.findById(id)
                .map(speaker -> {
                    speaker.setFullName(request.getFullName());
                    speaker.setBiography(request.getBiography());
                    speaker.setProfilePicture(request.getProfilePicture());
                    speaker.setWebsite(request.getWebsite());
                    speaker.setLinkedin(request.getLinkedin());
                    speaker.setGithub(request.getGithub());
                    speaker.setTitle(request.getTitle());
                    speaker.setCompany(request.getCompany());
                    speaker.setTwitter(request.getTwitter());
                    speaker.setTags(request.getTags());
                    return speakerRepository.save(speaker);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found with id: " + id));
    }

    public void deleteSpeaker(Long id) {
        if (!speakerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Speaker not found with id: " + id);
        }
        speakerRepository.deleteById(id);
    }
}
