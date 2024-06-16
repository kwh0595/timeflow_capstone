package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long sid;
    private String sname;
    private String scontents;
    private LocalDateTime startdate; // 필드명 수정
    private LocalDateTime enddate;
    private String sprocess;
    private String scolor;
}
