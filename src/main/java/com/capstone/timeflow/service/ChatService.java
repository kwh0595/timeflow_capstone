package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ChatGPTResponse;
import com.capstone.timeflow.entity.ChatEntity;
import com.capstone.timeflow.entity.JoinTeamEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
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

    private final ChatGptService chatGptService;


    public ChatEntity createChat(Long teamId, String sender, String message) {
        TeamEntity team = teamRepository.findByTeamId(teamId).orElseThrow();  //방 찾기 -> 없는 방일 경우 여기서 예외처리
        return chatRepository.save(ChatEntity.createChat(team, sender, message));
    }

    public List<ChatEntity> findAllChatByTeamId(Long teamId) {
        return chatRepository.findAllByTeam_TeamId(teamId);
    }
    //gpt응답 받아오는 메서드
    public ChatGPTResponse getGptResponse(String prompt) {
        return chatGptService.chat(prompt);
    }
}