package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.QuestionRequest;
import com.example.eventsync_backend.entity.Question;
import com.example.eventsync_backend.entity.Session;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.NotFoundException;
import com.example.eventsync_backend.repository.QuestionRepository;
import com.example.eventsync_backend.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public Question askQuestion(Integer sessionId, QuestionRequest request) {
        if (!StringUtils.hasText(request.getContent())) {
            throw new BadRequestException("Question content is required");
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found with id=" + sessionId));

        Instant now = Instant.now();
        if (now.isBefore(session.getStartTime()) || now.isAfter(session.getEndTime())) {
            throw new BadRequestException("Cannot ask question: session is not live");
        }

        Question question = new Question();
        question.setContent(request.getContent());
        question.setAuthorName(request.getAuthorName());
        question.setUpvotes(0);
        question.setSessionId(sessionId);
        question.setCreatedAt(now);
        return questionRepository.save(question);
    }

    @Transactional
    public void upvoteQuestion(Integer questionId) {
        if (questionRepository.findById(questionId).isEmpty()) {
            throw new NotFoundException("Question not found with id=" + questionId);
        }
        questionRepository.incrementUpvotes(questionId);
    }

    public List<Question> getQuestionsForSession(Integer sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found with id=" + sessionId));
        Instant now = Instant.now();
        if (now.isBefore(session.getStartTime()) || now.isAfter(session.getEndTime())) {
            throw new BadRequestException("Questions are only visible during live session");
        }
        return questionRepository.findBySessionIdOrderByUpvotesDesc(sessionId);
    }
}
