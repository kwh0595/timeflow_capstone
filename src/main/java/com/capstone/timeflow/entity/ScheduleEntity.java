package com.capstone.timeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//
@Entity
@Getter
@Setter
@Table(name = "SCHEDULE")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Long sid;

    @Column(name = "sname", nullable = false, length = 100)
    private String sname;

    @Column(name = "scontents", length = 255)
    private String scontents;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "sprocess", length = 50)
    private String sprocess;

    @Column(name = "scolor", nullable=false)
    private String scolor;


    @ManyToOne
    @JoinColumn(name = "teamId", nullable = true)
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "registrarId", nullable = false)
    private UserEntity registrar;

    //@ManyToMany
    //@JoinTable(
    //        name = "JoinTeamSchedule",
    //        joinColumns = @JoinColumn(name = "sid"),
    //        inverseJoinColumns = @JoinColumn(name = "userId")
   // )
    @ManyToMany
    @JoinTable(
            name = "join_team_schedule",
            joinColumns = @JoinColumn(name = "sid", referencedColumnName = "sid"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId")
    )
    private Set<UserEntity> assignees = new HashSet<>();

}
