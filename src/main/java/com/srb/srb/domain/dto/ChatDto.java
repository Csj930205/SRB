package com.srb.srb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
