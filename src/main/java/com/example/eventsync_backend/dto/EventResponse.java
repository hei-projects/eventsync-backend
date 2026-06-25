package com.example.eventsync_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String location;

    private String longDescription;

    private String venue;

    private String coverImage;

    private String tags;

    @JsonProperty("isLive")
    private boolean live;

    private String status;
}