package com.capstone.timeflow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private String roomName;

    //팀 개설시, 자동으로 채팅방이 개설.
    public static ChatRoom create(String name){
        ChatRoom room= new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }
}
