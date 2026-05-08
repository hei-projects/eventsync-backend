package com.example.eventsync_backend.dto;

import lombok.Data;

@Data
public class SpeakerRequest {
    private String fullName;
    private String profilePhotoUrl;
    private String biography;
    private String externalLinks;
}