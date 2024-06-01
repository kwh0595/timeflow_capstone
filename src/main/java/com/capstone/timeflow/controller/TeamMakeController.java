package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.SessionUser;
import com.capstone.timeflow.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamMakeController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private SessionUser sessionUser;

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestParam String name, HttpSession currentUser) {
        Long userId = (Long) currentUser.getAttribute("userId");
        System.out.println(currentUser.getAttribute("userId"));
        // 여기 그 userentity 가져오는거 서비스 짜야됨
        UserEntity user;

        if (userId != null) {
            user = sessionUser.getUserById(userId);
        } else {
            // 사용자 ID가 세션에 없을 경우 처리
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        try {
            teamService.createTeam(name, user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Team Created successfully");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}