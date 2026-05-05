package com.example.eventsync_backend.dto;

import lombok.Data;

@Data
public class QuestionRequest {
    private String content;
    private String authorName;
}