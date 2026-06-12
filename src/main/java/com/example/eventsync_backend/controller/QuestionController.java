

package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateQuestionRequest;
import com.example.eventsync_backend.dto.QuestionResponse;
import com.example.eventsync_backend.mapper.QuestionMapper;
import com.example.eventsync_backend.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/sessions/{sessionId}/questions")
    public List<QuestionResponse> getQuestionsBySession(
            @PathVariable Long sessionId
    ) {
        return questionService.getQuestionsBySession(sessionId)
                .stream()
                .map(QuestionMapper::toResponse)
                .toList();
    }

    @PostMapping("/sessions/{sessionId}/questions")
    public QuestionResponse createQuestion(
            @PathVariable Long sessionId,
            @Valid @RequestBody CreateQuestionRequest request
    ) {
        return QuestionMapper.toResponse(
                questionService.createQuestion(sessionId, request)
        );
    }

    @PostMapping("/questions/{questionId}/upvote")
    public QuestionResponse upvoteQuestion(
            @PathVariable Long questionId
    ) {
        return QuestionMapper.toResponse(
                questionService.upvoteQuestion(questionId)
        );
    }
}


