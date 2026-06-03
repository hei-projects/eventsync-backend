package com.example.eventsync_backend.service;

import com.example.eventsync_backend.entity.Question;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.repository.QuestionRepository;
import com.example.eventsync_backend.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public QuestionService(
            QuestionRepository questionRepository,
            SessionRepository sessionRepository,
            SessionService sessionService
    ) {
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
    }

    public List<Question> getQuestionsBySession(Long sessionId) {

        return questionRepository
                .findBySessionIdOrderByUpvotesDesc(sessionId);
    }

    public Question createQuestion(Long sessionId, Question question) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found"));

        if (!sessionService.isSessionLive(session)) {
            throw new BadRequestException(
                    "Questions are allowed only during live session"
            );
        }

        question.setSession(session);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpvotes(0);

        return questionRepository.save(question);
    }

    public Question upvoteQuestion(Long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));

        question.setUpvotes(question.getUpvotes() + 1);

        return questionRepository.save(question);
    }
}