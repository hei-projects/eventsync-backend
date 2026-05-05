package com.example.eventsync_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;
    private String content;
    private String authorName;
    private Integer upvotes;
    private Integer sessionId;
    private Instant createdAt;
}