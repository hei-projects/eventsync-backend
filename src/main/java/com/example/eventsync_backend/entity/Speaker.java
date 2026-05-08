package com.example.eventsync_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    private Integer id;
    private String fullName;
    private String profilePhotoUrl;
    private String biography;
    private String externalLinks;
    private List<Session> sessions;
}