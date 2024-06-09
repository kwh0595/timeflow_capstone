package com.capstone.timeflow.entity;

import com.capstone.timeflow.dto.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime teamCreationDate;

    @Column(nullable = false)
    private String joinCode;

    public TeamEntity() {
    }

    public TeamEntity(String name) {
        this.teamName = name;
        this.joinCode = generateInvitationCode();
    }

    private String generateInvitationCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public static TeamEntity toTeamEntity(TeamDTO teamDTO){
        TeamEntity teamEntity = new TeamEntity(); //userEntity 객체 생성
        teamEntity.setTeamName(teamDTO.getTeamName()); //이름
        // 가입날짜가 null이면 현재 시간을 사용
        if (teamEntity.getTeamCreationDate() == null) {
            teamEntity.setTeamCreationDate(LocalDateTime.now());
        } else {
            teamEntity.setTeamCreationDate(teamDTO.getTeamCreationDate());
        }
        return teamEntity;
    }
}
