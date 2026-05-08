package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.SpeakerRequest;
import com.example.eventsync_backend.entity.Speaker;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.NotFoundException;
import com.example.eventsync_backend.repository.SessionRepository;
import com.example.eventsync_backend.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SessionRepository sessionRepository;

    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    public Speaker getById(Integer id) {
        return speakerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Speaker not found with id=" + id));
    }

    @Transactional
    public Speaker create(SpeakerRequest request) {
        validateSpeakerRequest(request);
        Speaker speaker = new Speaker();
        speaker.setFullName(request.getFullName());
        speaker.setProfilePhotoUrl(request.getProfilePhotoUrl());
        speaker.setBiography(request.getBiography());
        speaker.setExternalLinks(request.getExternalLinks());
        return speakerRepository.save(speaker);
    }

    @Transactional
    public Speaker update(Integer id, SpeakerRequest request) {
        getById(id);
        validateSpeakerRequest(request);
        Speaker speaker = new Speaker();
        speaker.setId(id);
        speaker.setFullName(request.getFullName());
        speaker.setProfilePhotoUrl(request.getProfilePhotoUrl());
        speaker.setBiography(request.getBiography());
        speaker.setExternalLinks(request.getExternalLinks());
        return speakerRepository.save(speaker);
    }

    @Transactional
    public void delete(Integer id) {
        getById(id);
        speakerRepository.deleteById(id);
    }

    public List<Speaker> getSpeakersBySession(Integer sessionId) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found with id=" + sessionId));
        return speakerRepository.findBySessionId(sessionId);
    }

    private void validateSpeakerRequest(SpeakerRequest req) {
        if (!StringUtils.hasText(req.getFullName())) {
            throw new BadRequestException("Speaker full name is required");
        }
    }
}