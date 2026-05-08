package com.example.eventsync_backend.repository;

import com.example.eventsync_backend.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Room> roomRowMapper = (rs, rowNum) -> {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setName(rs.getString("name"));
        return room;
    };

    public List<Room> findAll() {
        String sql = "SELECT id, name FROM room";
        return jdbcTemplate.query(sql, roomRowMapper);
    }

    public Optional<Room> findById(Integer id) {
        String sql = "SELECT id, name FROM room WHERE id = ?";
        List<Room> result = jdbcTemplate.query(sql, roomRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Room save(Room room) {
        if (room.getId() == null) {
            return insert(room);
        } else {
            update(room);
            return room;
        }
    }

    private Room insert(Room room) {
        String sql = "INSERT INTO room (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getName());
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        room.setId((Integer) keys.get("id"));
        return room;
    }

    private void update(Room room) {
        String sql = "UPDATE room SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, room.getName(), room.getId());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM room WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}