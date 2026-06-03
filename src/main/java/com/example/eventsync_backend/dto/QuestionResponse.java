package com.example.eventsync_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {

    private Long id;

    private String content;

    private String authorName;

    private Integer upvotes;

    private LocalDateTime createdAt;
}