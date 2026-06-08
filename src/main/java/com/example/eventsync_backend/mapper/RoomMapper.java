package com.example.eventsync_backend.mapper;

import com.example.eventsync_backend.dto.RoomResponse;
import com.example.eventsync_backend.entity.Room;

public class RoomMapper {

    public static RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .build();
    }
}
