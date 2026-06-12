CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS speakers (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(200) NOT NULL,
    profile_picture TEXT,
    biography TEXT,
    website TEXT,
    linkedin TEXT,
    github TEXT
);

CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    capacity INTEGER,
    event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    room_id INTEGER NOT NULL REFERENCES rooms(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS session_speakers (
    session_id INTEGER NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    speaker_id INTEGER NOT NULL REFERENCES speakers(id) ON DELETE CASCADE,
    PRIMARY KEY (session_id, speaker_id)
);

CREATE TABLE IF NOT EXISTS questions (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    author_name VARCHAR(100),
    upvotes INTEGER DEFAULT 0,
    session_id INTEGER NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_session_event_id ON sessions(event_id);
CREATE INDEX idx_session_room_id ON sessions(room_id);
CREATE INDEX idx_question_session_id ON questions(session_id);
CREATE INDEX idx_session_start_time ON sessions(start_time);
CREATE INDEX idx_session_end_time ON sessions(end_time);
