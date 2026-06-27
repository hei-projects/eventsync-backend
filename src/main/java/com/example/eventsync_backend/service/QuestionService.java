package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.CreateQuestionRequest;
import com.example.eventsync_backend.entity.Question;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.QuestionRepository;
import com.example.eventsync_backend.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    private final Map<Long, Long> upvoteCooldowns = new ConcurrentHashMap<>();
    private static final long COOLDOWN_MS = 30_000;

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
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));

        if (LocalDateTime.now().isBefore(session.getStartTime())) {
            return List.of();
        }

        return questionRepository
                .findBySessionIdOrderByUpvotesDesc(sessionId);
    }

    public Question createQuestion(Long sessionId, CreateQuestionRequest request) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));

        if (!sessionService.isSessionLive(session)) {
            throw new BadRequestException(
                    "Questions are allowed only during live session"
            );
        }

        Question question = Question.builder()
                .content(request.getContent())
                .authorName(request.getAuthorName())
                .session(session)
                .createdAt(LocalDateTime.now())
                .upvotes(0)
                .build();

        return questionRepository.save(question);
    }

    public Question upvoteQuestion(Long questionId) {
        long now = System.currentTimeMillis();
        Long last = upvoteCooldowns.get(questionId);
        if (last != null && (now - last) < COOLDOWN_MS) {
            throw new BadRequestException("Veuillez patienter avant de revoter");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        question.setUpvotes(question.getUpvotes() + 1);
        Question saved = questionRepository.save(question);

        upvoteCooldowns.put(questionId, now);
        return saved;
    }
}
