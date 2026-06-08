package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.QuestionResponse;
import com.example.eventsync_backend.entity.Question;

public class QuestionMapper {

    public static QuestionResponse toResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .authorName(question.getAuthorName())
                .upvotes(question.getUpvotes())
                .createdAt(question.getCreatedAt())
                .build();
    }
}
