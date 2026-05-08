package com.example.eventsync_backend.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class SessionRequest {
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Integer roomId;
    private Integer capacity;
    private Integer eventId;
    private List<Integer> speakerIds;
}