
package com.example.eventsync_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpeakerRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String profilePicture;

    private String biography;

    private String website;
    private String linkedin;
    private String github;
}
