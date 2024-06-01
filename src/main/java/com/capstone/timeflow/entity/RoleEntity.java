package com.capstone.timeflow.entity;

import com.capstone.timeflow.initialdata.enumRole;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private TeamEntity teamId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private enumRole role = enumRole.MEMBER; // 기본값을 MEMBER로 설정

    public RoleEntity() {}

    public RoleEntity(UserEntity user, TeamEntity team, enumRole role) {
        this.userId = user;
        this.teamId = team;
        this.role = role;
    }

    public RoleEntity(UserEntity user, TeamEntity team) {
        this.userId = user;
        this.teamId = team;
        this.role = enumRole.MEMBER;
    }
}
