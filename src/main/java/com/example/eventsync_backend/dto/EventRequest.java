package com.example.eventsync_backend.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class EventRequest {
    private String title;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private String location;
}