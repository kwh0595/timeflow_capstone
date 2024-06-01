package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;

public interface ScheduleService {

     //스케줄 관리를 위한 서비스 인터페이스.
    ScheduleEntity createSchedule(ScheduleDTO scheduleDTO);
    boolean updateSchedule(Long sid, String sprocess);
    boolean deleteSchedule(Long sid);
}
