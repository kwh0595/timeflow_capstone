package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long sid;
    private String sname;
    private String scontents;
    private LocalDateTime startdate; // 필드명 수정
    private LocalDateTime enddate;
    private String sprocess;


    // Getter와 Setter 메소드
    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getScontents() {
        return scontents;
    }

    public void setScontents(String scontents) {
        this.scontents = scontents;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime scheduleTime) {
        this.startdate = scheduleTime;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime endTime) {
        this.enddate = endTime;
    }

    public String getSprocess() {
        return sprocess;
    }

    public void setSprocess(String sprocess) {
        this.sprocess = sprocess;
    }

    //추후 scheduleJoin 테이블 생성후 관게 추가 설정

}