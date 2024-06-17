package com.capstone.timeflow.service;

import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.JoinTeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class JoinTeamRepositoryImpl implements JoinTeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByTeamIdAndUserId(TeamEntity teamId, UserEntity userId) {
        String query = "SELECT COUNT(r) > 0 FROM JoinTeamEntity r WHERE r.teamId = :teamId AND r.userId = :userId";
        return (boolean) entityManager.createQuery(query)
                .setParameter("teamId", teamId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public void deleteByTeamId(TeamEntity teamId) {
        String deleteQuery = "DELETE FROM JoinTeamEntity r WHERE r.teamId.id = :teamId";
        entityManager.createQuery(deleteQuery)
                .setParameter("teamId", teamId)
                .executeUpdate();
    }
}