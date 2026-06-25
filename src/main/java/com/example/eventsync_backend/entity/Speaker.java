package com.example.eventsync_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String profilePicture;

    @Column(length = 5000)
    private String biography;

    private String website;
    private String linkedin;
    private String github;

    private String title;

    private String company;

    private String twitter;

    @Column(length = 1000)
    private String tags;

    @ManyToMany(mappedBy = "speakers")
    private List<Session> sessions;
}
