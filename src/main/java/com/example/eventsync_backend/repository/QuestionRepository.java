package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    List<Question> findBySessionIdOrderByUpvotesDesc(Long sessionId);
}