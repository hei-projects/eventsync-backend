

package com.example.eventsync_backend.service;

import com.example.eventsync_backend.dto.CreateRoomRequest;
import com.example.eventsync_backend.entity.Room;
import com.example.eventsync_backend.exception.ResourceNotFoundException;
import com.example.eventsync_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public Room createRoom(CreateRoomRequest request) {
        Room room = Room.builder()
                .name(request.getName())
                .build();
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, CreateRoomRequest request) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setName(request.getName());
                    return roomRepository.save(room);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
}

