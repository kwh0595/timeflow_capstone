package com.capstone.timeflow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private Long sid;
    private String sname;
    private String scontents;
    private LocalDateTime startdate; // 필드명 수정
    private LocalDateTime enddate;
    private String sprocess;
    private String scolor;

    private List<String> assigneeUsernames;//add

    public ScheduleDTO(Long sid, String sname, String scontents, LocalDateTime startdate, LocalDateTime enddate, String sprocess, String scolor) {
        this.sid = sid;
        this.sname = sname;
        this.scontents = scontents;
        this.startdate = startdate;
        this.enddate = enddate;
        this.sprocess = sprocess;
        this.scolor = scolor;

    }
}