package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    public enum MessageType {
        ENTER,
        TALK
    }

    private Long teamId;
    private String sender;
    private String message;
    private LocalDateTime sendDate;
    private MessageType messageType;

    public MessageType getType() {
        return this.messageType;
    }
}