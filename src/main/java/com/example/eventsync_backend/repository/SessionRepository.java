package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Session> sessionRowMapper = (rs, rowNum) -> {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setTitle(rs.getString("title"));
        session.setDescription(rs.getString("description"));
        session.setStartTime(rs.getTimestamp("start_time").toInstant());
        session.setEndTime(rs.getTimestamp("end_time").toInstant());
        session.setRoomId(rs.getInt("room_id"));
        session.setCapacity(rs.getInt("capacity"));
        session.setEventId(rs.getInt("event_id"));
        return session;
    };

    public List<Session> findAll() {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session ORDER BY start_time";
        return jdbcTemplate.query(sql, sessionRowMapper);
    }

    public Optional<Session> findById(Integer id) {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session WHERE id = ?";
        List<Session> result = jdbcTemplate.query(sql, sessionRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Session> findByEventId(Integer eventId) {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session WHERE event_id = ? ORDER BY start_time";
        return jdbcTemplate.query(sql, sessionRowMapper, eventId);
    }

    public List<Session> findLiveSessions(Instant now) {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session WHERE start_time <= ? AND end_time >= ?";
        return jdbcTemplate.query(sql, sessionRowMapper, Timestamp.from(now), Timestamp.from(now));
    }

    public List<Session> findLiveByEventId(Integer eventId, Instant now) {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session WHERE event_id = ? AND start_time <= ? AND end_time >= ?";
        return jdbcTemplate.query(sql, sessionRowMapper, eventId, Timestamp.from(now), Timestamp.from(now));
    }

    public List<Session> findByRoomId(Integer roomId) {
        String sql = "SELECT id, title, description, start_time, end_time, room_id, capacity, event_id FROM session WHERE room_id = ? ORDER BY start_time";
        return jdbcTemplate.query(sql, sessionRowMapper, roomId);
    }

    public Session save(Session session) {
        if (session.getId() == null) {
            return insert(session);
        } else {
            update(session);
            return session;
        }
    }

    private Session insert(Session session) {
        String sql = "INSERT INTO session (title, description, start_time, end_time, room_id, capacity, event_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, session.getTitle());
            ps.setString(2, session.getDescription());
            ps.setTimestamp(3, Timestamp.from(session.getStartTime()));
            ps.setTimestamp(4, Timestamp.from(session.getEndTime()));
            ps.setInt(5, session.getRoomId());
            if (session.getCapacity() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, session.getCapacity());
            }            ps.setInt(7, session.getEventId());
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        session.setId((Integer) keys.get("id"));
        return session;
    }

    private void update(Session session) {
        String sql = "UPDATE session SET title=?, description=?, start_time=?, end_time=?, room_id=?, capacity=?, event_id=? WHERE id=?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, session.getTitle());
            ps.setString(2, session.getDescription());
            ps.setTimestamp(3, Timestamp.from(session.getStartTime()));
            ps.setTimestamp(4, Timestamp.from(session.getEndTime()));
            ps.setInt(5, session.getRoomId());
            if (session.getCapacity() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, session.getCapacity());
            }
            ps.setInt(7, session.getEventId());
            ps.setInt(8, session.getId());
            return ps;
        });
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM session WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addSpeakerToSession(Integer sessionId, Integer speakerId) {
        String sql = "INSERT INTO session_speaker (session_id, speaker_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, sessionId, speakerId);
    }

    public void deleteAllSpeakersFromSession(Integer sessionId) {
        String sql = "DELETE FROM session_speaker WHERE session_id = ?";
        jdbcTemplate.update(sql, sessionId);
    }
}