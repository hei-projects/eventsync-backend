package com.example.eventsync_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;
    private String authorName;
    private Integer upvotes;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}