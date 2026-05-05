package com.example.eventsync_backend.service;

import com.example.eventsync_backend.entity.Room;
import com.example.eventsync_backend.exception.BadRequestException;
import com.example.eventsync_backend.exception.NotFoundException;
import com.example.eventsync_backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room getById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id=" + id));
    }

    @Transactional
    public Room create(Room room) {
        validateRoom(room);
        return roomRepository.save(room);
    }

    @Transactional
    public Room update(Integer id, Room room) {
        getById(id);
        validateRoom(room);
        room.setId(id);
        return roomRepository.save(room);
    }

    @Transactional
    public void delete(Integer id) {
        getById(id);
        roomRepository.deleteById(id);
    }

    private void validateRoom(Room room) {
        if (!StringUtils.hasText(room.getName())) {
            throw new BadRequestException("Room name is required");
        }
    }
}
