package com.example.eventsync_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 5000)
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;

    @OneToMany(mappedBy = "event")
    private List<Session> sessions;
}