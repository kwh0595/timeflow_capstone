package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.JoinTeamService;
import com.capstone.timeflow.service.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// JoinTeamController.java
@RestController
@RequestMapping("/team")
public class TeamJoinController {

    @Autowired
    private JoinTeamService joinTeamService;
    @Autowired
    private SessionUser sessionUser;

    @PostMapping("/join")
    public ResponseEntity<String> joinTeam(@RequestParam String joinCode, HttpSession session) {
        // 세션에서 사용자 ID 가져오기
        // 이 예시에서는 세션에 사용자가 'userId'로 저장되어 있다고 가정
        Long userId = (Long) session.getAttribute("userId");

        UserEntity user;
        if (userId != null) {
            user = sessionUser.getUserById(userId);
        } else {
            // 사용자 ID가 세션에 없을 경우 처리
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }


        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login is required.");
        }

        boolean success = joinTeamService.addUserToTeam(joinCode, user);

        if (!success) {
            return ResponseEntity.badRequest().body("Invalid joinCode or already a member of the team.");
        }

        return ResponseEntity.ok("Successfully joined the team.");
    }
}