package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ScheduleService {

    ScheduleEntity createPersonalSchedule(ScheduleDTO scheduleDTO, HttpSession session);
    ScheduleEntity createTeamSchedule(ScheduleDTO scheduleDTO, Long teamId, HttpSession session);
    boolean updateSchedule(Long sid, ScheduleDTO scheduleDTO, HttpSession session);

    boolean deleteSchedule(Long sid, HttpSession session);

    // 로그인된 사용자가 시행자로 되어 있는 일정 조회 메서드
    List<ScheduleEntity> getSchedulesByAssignee(Long userId);

    List<ScheduleEntity> getUserSchedules(Long userId);
}
