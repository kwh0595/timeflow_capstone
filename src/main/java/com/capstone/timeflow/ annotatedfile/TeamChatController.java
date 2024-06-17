/*
package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.TeamChatService;
import com.capstone.timeflow.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-rooms")
public class TeamChatController {

    private final TeamChatService teamChatService;

    public TeamChatController(TeamChatService teamChatService) {
        this.teamChatService = teamChatService;
    }

    @GetMapping
    public ResponseEntity<List<TeamEntity>> getAllChatRooms(@AuthenticationPrincipal UserEntity user) {
        List<TeamEntity> chatRooms = teamChatService.findAllByUserId(user.getUserId());
        return ResponseEntity.ok(chatRooms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long id) {
        teamChatService.deleteById(id);
        // 채팅방 삭제 후 클라이언트에게 업데이트된 채팅방 목록 전송
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamEntity> updateChatRoom(@PathVariable Long id, @RequestBody TeamEntity updatedChatRoom) {
        TeamEntity savedChatRoom = teamChatService.save(updatedChatRoom);
        // 채팅방 수정 후 클라이언트에게 업데이트된 채팅방 정보 전송
        return ResponseEntity.ok(savedChatRoom);
    }
}
*/
