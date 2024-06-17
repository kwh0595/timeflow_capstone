package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByJoinCode(String joinCode);
    Optional<TeamEntity> findByTeamId(Long teamId);
    List<TeamEntity> findByTeamIdIn(List<Long> teamIds);
    TeamEntity save(TeamEntity teamEntity);
    void deleteByTeamId(TeamEntity teamId);
}