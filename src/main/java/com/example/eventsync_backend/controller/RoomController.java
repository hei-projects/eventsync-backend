

package com.example.eventsync_backend.controller;

import com.example.eventsync_backend.dto.CreateRoomRequest;
import com.example.eventsync_backend.dto.RoomResponse;
import com.example.eventsync_backend.dto.SessionResponse;
import com.example.eventsync_backend.mapper.RoomMapper;
import com.example.eventsync_backend.mapper.SessionMapper;
import com.example.eventsync_backend.service.RoomService;
import com.example.eventsync_backend.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final SessionService sessionService;

    public RoomController(RoomService roomService, SessionService sessionService) {
        this.roomService = roomService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(RoomMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return RoomMapper.toResponse(roomService.getRoomById(id));
    }

    @PostMapping
    public RoomResponse createRoom(@Valid @RequestBody CreateRoomRequest request) {
        return RoomMapper.toResponse(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(@PathVariable Long id,
                                   @Valid @RequestBody CreateRoomRequest request) {
        return RoomMapper.toResponse(roomService.updateRoom(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    @GetMapping("/{roomId}/sessions")
    public List<SessionResponse> getSessionsByRoom(@PathVariable Long roomId) {
        return sessionService.getSessionsByRoom(roomId)
                .stream()
                .map(SessionMapper::toResponse)
                .toList();
    }
}

