package com.capstone.timeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "JoinTeam")
public class JoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private TeamEntity teamId;

    @Column(nullable = false, length = 6, columnDefinition = "varchar(6) default 'member'")
    private String role;

    @Column(name = "teamColor")
    private String teamColor;
}
