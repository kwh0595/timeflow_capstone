/*package com.capstone.timeflow.handler;

import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChatRoomWebSocketHandler extends TextWebSocketHandler {

    private final TeamService teamService;
    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> webSocketSessions = new HashSet<>();

    public ChatRoomWebSocketHandler(TeamService teamService, ObjectMapper objectMapper) {
        this.teamService = teamService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
        sendChatRoomList(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    private void sendChatRoomList(WebSocketSession session) {
        try {
            Long userId = (Long) session.getAttributes().get("userId");
            List<TeamEntity> List = teamService.getTeamsByUserId(userId);
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(List));
            session.sendMessage(textMessage);
        } catch (IOException e) {
            webSocketSessions.remove(session);
        }
    }

    private void broadcastChatRoomList() {
        for (WebSocketSession session : webSocketSessions) {
            try {
                // 세션에서 사용자 ID 가져오기
                String userId = (String) session.getAttributes().get("userId");
                List<TeamEntity> chatRoomList = teamService.getTeamsByUserId(Long.parseLong(userId));
                TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatRoomList));
                session.sendMessage(textMessage);
            } catch (IOException e) {
                webSocketSessions.remove(session);
            }
        }
    }
}*/