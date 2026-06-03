package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.entity.Question;
import com.example.eventsync_backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/sessions/{sessionId}/questions")
    public List<Question> getQuestionsBySession(
            @PathVariable Long sessionId
    ) {
        return questionService.getQuestionsBySession(sessionId);
    }

    @PostMapping("/sessions/{sessionId}/questions")
    public Question createQuestion(
            @PathVariable Long sessionId,
            @RequestBody Question question
    ) {
        return questionService.createQuestion(sessionId, question);
    }

    @PostMapping("/questions/{questionId}/upvote")
    public Question upvoteQuestion(
            @PathVariable Long questionId
    ) {
        return questionService.upvoteQuestion(questionId);
    }
}