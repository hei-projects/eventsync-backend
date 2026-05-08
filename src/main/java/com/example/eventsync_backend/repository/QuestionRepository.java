package com.example.eventsync_backend.repository;


import com.example.eventsync_backend.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Question> questionRowMapper = (rs, rowNum) -> {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setContent(rs.getString("content"));
        question.setAuthorName(rs.getString("author_name"));
        question.setUpvotes(rs.getInt("upvotes"));
        question.setSessionId(rs.getInt("session_id"));
        question.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return question;
    };

    public List<Question> findBySessionIdOrderByUpvotesDesc(Integer sessionId) {
        String sql = "SELECT id, content, author_name, upvotes, session_id, created_at FROM question WHERE session_id = ? ORDER BY upvotes DESC";
        return jdbcTemplate.query(sql, questionRowMapper, sessionId);
    }

    public Optional<Question> findById(Integer id) {
        String sql = "SELECT id, content, author_name, upvotes, session_id, created_at FROM question WHERE id = ?";
        List<Question> result = jdbcTemplate.query(sql, questionRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Question save(Question question) {
        if (question.getId() == null) {
            return insert(question);
        } else {
            update(question);
            return question;
        }
    }

    private Question insert(Question question) {
        String sql = "INSERT INTO question (content, author_name, upvotes, session_id, created_at) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, question.getContent());
            ps.setString(2, question.getAuthorName());
            ps.setInt(3, question.getUpvotes() == null ? 0 : question.getUpvotes());
            ps.setInt(4, question.getSessionId());
            ps.setTimestamp(5, Timestamp.from(question.getCreatedAt() != null ? question.getCreatedAt() : Instant.now()));
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        question.setId((Integer) keys.get("id"));
        return question;
    }

    private void update(Question question) {
        String sql = "UPDATE question SET content=?, author_name=?, upvotes=? WHERE id=?";
        jdbcTemplate.update(sql, question.getContent(), question.getAuthorName(), question.getUpvotes(), question.getId());
    }

    public void incrementUpvotes(Integer questionId) {
        String sql = "UPDATE question SET upvotes = upvotes + 1 WHERE id = ?";
        jdbcTemplate.update(sql, questionId);
    }
}