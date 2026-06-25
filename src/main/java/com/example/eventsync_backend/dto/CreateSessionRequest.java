package com.example.eventsync_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private Integer capacity;

    @NotNull(message = "Event ID is required")
    private Long eventId;

    private Long roomId;

    @NotNull(message = "At least one speaker is required")
    private List<Long> speakerIds;

    private String track;

    private String level;

    private String tags;
}
