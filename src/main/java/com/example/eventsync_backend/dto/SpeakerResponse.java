package com.example.eventsync_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerResponse {

    private Long id;

    private String fullName;

    private String biography;

    private String profilePicture;

    private String website;

    private String linkedin;

    private String github;
}