package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpeakerRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Speaker> speakerRowMapper = (rs, rowNum) -> {
        Speaker speaker = new Speaker();
        speaker.setId(rs.getInt("id"));
        speaker.setFullName(rs.getString("full_name"));
        speaker.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        speaker.setBiography(rs.getString("biography"));
        speaker.setExternalLinks(rs.getString("external_links"));
        return speaker;
    };

    public List<Speaker> findAll() {
        String sql = "SELECT id, full_name, profile_photo_url, biography, external_links FROM speaker";
        return jdbcTemplate.query(sql, speakerRowMapper);
    }

    public Optional<Speaker> findById(Integer id) {
        String sql = "SELECT id, full_name, profile_photo_url, biography, external_links FROM speaker WHERE id = ?";
        List<Speaker> result = jdbcTemplate.query(sql, speakerRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Speaker> findBySessionId(Integer sessionId) {
        String sql = "SELECT s.id, s.full_name, s.profile_photo_url, s.biography, s.external_links " +
                "FROM speaker s JOIN session_speaker ss ON s.id = ss.speaker_id WHERE ss.session_id = ?";
        return jdbcTemplate.query(sql, speakerRowMapper, sessionId);
    }

    public Speaker save(Speaker speaker) {
        if (speaker.getId() == null) {
            return insert(speaker);
        } else {
            update(speaker);
            return speaker;
        }
    }

    private Speaker insert(Speaker speaker) {
        String sql = "INSERT INTO speaker (full_name, profile_photo_url, biography, external_links) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, speaker.getFullName());
            ps.setString(2, speaker.getProfilePhotoUrl());
            ps.setString(3, speaker.getBiography());
            ps.setString(4, speaker.getExternalLinks());
            return ps;
        }, keyHolder);
        speaker.setId(keyHolder.getKey().intValue());
        return speaker;
    }

    private void update(Speaker speaker) {
        String sql = "UPDATE speaker SET full_name=?, profile_photo_url=?, biography=?, external_links=? WHERE id=?";
        jdbcTemplate.update(sql, speaker.getFullName(), speaker.getProfilePhotoUrl(),
                speaker.getBiography(), speaker.getExternalLinks(), speaker.getId());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM speaker WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}