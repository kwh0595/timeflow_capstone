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

    List<ScheduleEntity> getUserSchedules(Long userId);
}
