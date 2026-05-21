package com.example.eventsync_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "speakers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String profilePicture;

    @Column(length = 5000)
    private String biography;
    private String website;
    private String linkedin;
    private String github;

    @ManyToMany(mappedBy = "speakers")
    private List<Session> sessions;
}