package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.JoinTeamEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface JoinTeamRepository extends JpaRepository<JoinTeamEntity, Long> {
    boolean existsByTeamIdAndUserId(TeamEntity teamId, UserEntity userId);
    void deleteByTeamId(TeamEntity teamId);
    List<JoinTeamEntity> findByUserId(UserEntity userId);
    JoinTeamEntity findByTeamIdAndUserId(TeamEntity teamId, UserEntity userId);


}