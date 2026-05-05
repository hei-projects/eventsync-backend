package com.example.eventsync_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer id;
    private String title;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private String location;
    private List<Session> sessions;
}