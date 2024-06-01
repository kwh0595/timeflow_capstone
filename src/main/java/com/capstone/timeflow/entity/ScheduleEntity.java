package com.capstone.timeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

//추후 scheduleJoin 테이블 생성후 관게 추가 설정
}
