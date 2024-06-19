package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ChatBotResponse;
import com.capstone.timeflow.entity.ChatEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.repository.ChatRepository;

import com.capstone.timeflow.repository.TeamRepository;
import com.capstone.timeflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ChatRepository chatRepository;

    private final ChatBotService chatGptService;


    public ChatEntity createChat(Long teamId, String sender, String message) {
        TeamEntity team = teamRepository.findByTeamId(teamId).orElseThrow();  //방 찾기 -> 없는 방일 경우 여기서 예외처리
        return chatRepository.save(ChatEntity.createChat(team, sender, message));
    }

    public List<ChatEntity> findAllChatByTeamId(Long teamId) {
        return chatRepository.findAllByTeam_TeamId(teamId);
    }
}