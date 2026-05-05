package com.example.eventsync_backend.repository;


import com.example.eventsync_backend.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Event> eventRowMapper = (rs, rowNum) -> {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setDescription(rs.getString("description"));
        event.setStartDate(rs.getTimestamp("start_date").toInstant());
        event.setEndDate(rs.getTimestamp("end_date").toInstant());
        event.setLocation(rs.getString("location"));
        return event;
    };

    public List<Event> findAll() {
        String sql = "SELECT id, title, description, start_date, end_date, location FROM event ORDER BY start_date";
        return jdbcTemplate.query(sql, eventRowMapper);
    }

    public Optional<Event> findById(Integer id) {
        String sql = "SELECT id, title, description, start_date, end_date, location FROM event WHERE id = ?";
        List<Event> result = jdbcTemplate.query(sql, eventRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Event save(Event event) {
        if (event.getId() == null) {
            return insert(event);
        } else {
            update(event);
            return event;
        }
    }

    private Event insert(Event event) {
        String sql = "INSERT INTO event (title, description, start_date, end_date, location) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, Timestamp.from(event.getStartDate()));
            ps.setTimestamp(4, Timestamp.from(event.getEndDate()));
            ps.setString(5, event.getLocation());
            return ps;
        }, keyHolder);
        event.setId(keyHolder.getKey().intValue());
        return event;
    }

    private void update(Event event) {
        String sql = "UPDATE event SET title=?, description=?, start_date=?, end_date=?, location=? WHERE id=?";
        jdbcTemplate.update(sql, event.getTitle(), event.getDescription(),
                Timestamp.from(event.getStartDate()), Timestamp.from(event.getEndDate()),
                event.getLocation(), event.getId());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM event WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}