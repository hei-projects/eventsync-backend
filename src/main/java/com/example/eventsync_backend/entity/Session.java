package com.example.eventsync_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Integer id;
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Integer roomId;
    private Integer capacity;
    private Integer eventId;
    private List<Speaker> speakers;
    private List<Question> questions;
}