-- create database event_sync;

CREATE TABLE IF NOT EXISTS room (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS speaker (
                                       id SERIAL PRIMARY KEY,
                                       full_name VARCHAR(200) NOT NULL,
    profile_photo_url TEXT,
    biography TEXT,
    external_links TEXT
    );

CREATE TABLE IF NOT EXISTS event (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    location VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS session (
                                       id SERIAL PRIMARY KEY,
                                       title VARCHAR(200) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    room_id INTEGER NOT NULL REFERENCES room(id) ON DELETE RESTRICT,
    capacity INTEGER,
    event_id INTEGER NOT NULL REFERENCES event(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS session_speaker (
                                               session_id INTEGER REFERENCES session(id) ON DELETE CASCADE,
    speaker_id INTEGER REFERENCES speaker(id) ON DELETE CASCADE,
    PRIMARY KEY (session_id, speaker_id)
    );

CREATE TABLE IF NOT EXISTS question (
                                        id SERIAL PRIMARY KEY,
                                        content TEXT NOT NULL,
                                        author_name VARCHAR(100),
    upvotes INTEGER DEFAULT 0,
    session_id INTEGER NOT NULL REFERENCES session(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_session_event_id ON session(event_id);
CREATE INDEX idx_session_room_id ON session(room_id);
CREATE INDEX idx_question_session_id ON question(session_id);
CREATE INDEX idx_session_start_time ON session(start_time);
CREATE INDEX idx_session_end_time ON session(end_time);