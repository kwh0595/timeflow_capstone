package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.JoinTeamEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<JoinTeamEntity, Long> {
    boolean existsByTeamIdAndUserId(TeamEntity teamId, UserEntity userId);
    void deleteByTeamId(TeamEntity teamId);

    JoinTeamEntity findByTeamIdAndUserId(TeamEntity teamId, UserEntity userId);


}