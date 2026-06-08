package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.SpeakerResponse;
import com.example.eventsync_backend.entity.Speaker;

public class SpeakerMapper {

    public static SpeakerResponse toResponse(Speaker speaker) {
        return SpeakerResponse.builder()
                .id(speaker.getId())
                .fullName(speaker.getFullName())
                .biography(speaker.getBiography())
                .profilePicture(speaker.getProfilePicture())
                .website(speaker.getWebsite())
                .linkedin(speaker.getLinkedin())
                .github(speaker.getGithub())
                .build();
    }
}
