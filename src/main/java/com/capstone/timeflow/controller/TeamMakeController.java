package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.TeamDTO;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.JoinTeamRepository;
import com.capstone.timeflow.repository.TeamRepository;
import com.capstone.timeflow.repository.UserRepository;
import com.capstone.timeflow.service.JoinTeamService;
import com.capstone.timeflow.service.SessionUser;
import com.capstone.timeflow.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "TEAM", description = "팀 생성 삭제 참가")
@RestController
@RequestMapping("/team")
public class TeamMakeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private JoinTeamService joinTeamService;

    @Autowired
    private JoinTeamRepository joinTeamRepository;

    @Autowired
    private SessionUser sessionUser;

    @Operation(summary = "팀 참가", description = "joinCode를 통한 팀 참가")
    @GetMapping("/join")
    public ResponseEntity<Boolean> joinTeam(@RequestParam String joinCode, HttpSession session) {
        // 세션에서 사용자 ID 가져오기
        // 이 예시에서는 세션에 사용자가 'userId'로 저장되어 있다고 가정
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("userId : "+ userId);
        System.out.println("joinCode : "+ joinCode);

        UserEntity user;
        if (userId != null) {
            user = sessionUser.getUserById(userId);
        } else {
            // 사용자 ID가 세션에 없을 경우 처리
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }

        if (userId == null) {
            return ResponseEntity.ok(false);
        }

        boolean isSuccess = joinTeamService.addUserToTeam(joinCode, user);
        System.out.println("success : "+ isSuccess);

        if (!isSuccess) {
            System.out.println("이미 가입한 사용자입니다.");
            return ResponseEntity.ok(isSuccess);
        }

        System.out.println("joinSuccess");
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamEntity>> getUsersTeams(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("userId : " + userId);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = sessionUser.getUserById(userId);

        // 팀 ID 목록으로 팀 엔티티 목록 가져오기
        List<TeamEntity> teams = teamService.getTeamsForUser(user);
        System.out.println(session.getAttribute("teamList" + teams));

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @Operation(summary = "팀 생성", description = "teamName 입력을 통한 팀 생성 = joinCode 자동 생성")
    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestParam("teamName") String teamName, HttpSession currentUser) {
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
            TeamEntity team = teamService.createTeam(teamName, user);
            String joinCode = team.getJoinCode();
            System.out.println("JoinCode : " + joinCode);
            return ResponseEntity.ok(joinCode);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Operation(summary = "팀 삭제", description = "ROLE = LEADER인 경우 팀 삭제")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId, @AuthenticationPrincipal UserEntity user) {
        try {
            TeamEntity team = teamRepository.findById(teamId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."));
            teamService.deleteTeam(team, user);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamEntity> updateChatRoom(@PathVariable Long id, @RequestBody TeamEntity updatedChatRoom) {
        TeamEntity savedChatRoom = teamRepository.save(updatedChatRoom);
        // 채팅방 수정 후 클라이언트에게 업데이트된 채팅방 정보 전송
        return ResponseEntity.ok(savedChatRoom);
    }
}