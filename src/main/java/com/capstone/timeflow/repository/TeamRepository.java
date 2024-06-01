package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByJoinCode(String joinCode);
    Optional<TeamEntity> findByTeamId(Long teamId);
}