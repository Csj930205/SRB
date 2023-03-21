package com.srb.srb.domain.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
public class ChatRoomDto {
    private String roomId;
    private String roomName;
    private long userCount;

    private HashMap<String, String> userList = new HashMap<>();

    public ChatRoomDto create(String roomName) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.roomName = roomName;
        return chatRoomDto;
    }
}
