package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.TeamDTO;
import com.capstone.timeflow.dto.UserResponse;
import com.capstone.timeflow.entity.JoinTeamEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.initialdata.enumRole;
import com.capstone.timeflow.repository.JoinTeamRepository;
import com.capstone.timeflow.repository.TeamRepository;
import com.capstone.timeflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JoinTeamRepository joinTeamRepository;

    @Autowired
    private JoinCodeService joinCodeService;

    public TeamService(TeamRepository teamRepository, JoinTeamRepository joinTeamRepository) {
        this.teamRepository = teamRepository;
        this.joinTeamRepository = joinTeamRepository;
    }

    // 팀 생성 메소드
    public TeamEntity createTeam(String name, UserEntity creator) {
        TeamEntity team = new TeamEntity();
        team.setTeamName(name);
        team.setJoinCode(joinCodeService.joinCodeGenerate((int)(Math.random() * 6) + 5));

        // 팀 저장
        team = teamRepository.save(team);

        // 사용자를 팀의 리더로 설정
        // 팀 생성자를 팀의 리더로 설정
        JoinTeamEntity roleEntity = new JoinTeamEntity(creator, team, enumRole.LEADER);
        joinTeamRepository.save(roleEntity);

        return team;
    }

    public List<TeamDTO> getTeamsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // JoinTeamEntity를 통해 teamId 리스트 가져오기
        List<JoinTeamEntity> joinTeams = joinTeamRepository.findByUserId(user);
        List<Long> teamIds = joinTeams.stream()
                .map(joinTeam -> joinTeam.getTeamId().getTeamId())
                .collect(Collectors.toList());

        // TeamEntity에서 teamId 리스트를 통해 team 가져오기
        List<TeamEntity> teams = teamRepository.findByTeamIdIn(teamIds);

        // TeamDTO로 변환
        return teams.stream()
                .map(team -> new TeamDTO(team.getTeamId(), team.getTeamName()))
                .collect(Collectors.toList());
    }

    public TeamEntity getTeamByJoinCode(String joinCode) {
        return teamRepository.findByJoinCode(joinCode).orElse(null);
    }

    public TeamEntity getTeamByTeamId(Long teamId) {
        return teamRepository.findByTeamId(teamId).orElse(null);
    }

    public void deleteTeam(TeamEntity teamEntity, UserEntity userEntity) {
        // 사용자의 role 확인
        JoinTeamEntity role = joinTeamRepository.findByTeamIdAndUserId(teamEntity, userEntity);
        if (role == null || !role.getRole().equals("LEADER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "팀 삭제 권한이 없습니다.");
        }

        // 팀 삭제 로직 실행
        deleteTeamAndRelatedData(teamEntity);
    }


    private void deleteTeamAndRelatedData(TeamEntity teamId) {
        // 관련 데이터 삭제
        joinTeamRepository.deleteByTeamId(teamId);

        // 팀 삭제
        teamRepository.deleteByTeamId(teamId);
    }
}