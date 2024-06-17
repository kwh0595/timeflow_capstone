/*
package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.TeamDTO;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamChatService {

    private final TeamRepository teamRepository;

    public TeamChatService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamEntity> findAllByUserId(Long userId) {
        return teamRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public void deleteById(Long teamId) {
        teamRepository.findByTeamId(teamId);
    }

    public TeamEntity save(TeamEntity teamEntity) {
        return teamRepository.save(teamEntity);
    }
}
*/
