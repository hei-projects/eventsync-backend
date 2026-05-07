package com.example.eventsync_backend.controller;


import com.example.eventsync_backend.dto.QuestionRequest;
import com.example.eventsync_backend.entity.Question;
import com.example.eventsync_backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions/{sessionId}/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> askQuestion(@PathVariable Integer sessionId,
                                                @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.askQuestion(sessionId, request));
    }

    @GetMapping
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(questionService.getQuestionsForSession(sessionId));
    }

    @PutMapping("/{questionId}/upvote")
    public ResponseEntity<Void> upvote(@PathVariable Integer questionId) {
        questionService.upvoteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}