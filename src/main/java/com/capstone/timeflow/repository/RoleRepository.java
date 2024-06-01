package com.capstone.timeflow.repository;

import com.capstone.timeflow.controller.TeamDeleteController;
import com.capstone.timeflow.entity.RoleEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    boolean existsByTeamIdAndUserId(TeamEntity teamId, UserEntity userId);
    void deleteByTeamId(TeamEntity teamId);
}