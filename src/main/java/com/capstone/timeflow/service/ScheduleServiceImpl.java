package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    //서비스를 구현함. 등록,수정,삭제
    @Override
    public ScheduleEntity createSchedule(ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setSname(scheduleDTO.getSname());
        schedule.setScontents(scheduleDTO.getScontents());
        schedule.setStartDate(scheduleDTO.getStartdate());
        schedule.setEndDate(scheduleDTO.getEnddate());
        schedule.setSprocess(scheduleDTO.getSprocess());
        return scheduleRepository.save(schedule);
    }

    @Override
    public boolean updateSchedule(Long sid, String sprocess) {
        ScheduleEntity schedule = scheduleRepository.findById(sid).orElse(null);
        if (schedule != null) {
            schedule.setSprocess(sprocess);
            scheduleRepository.save(schedule);
            return true;
        } else {
            log.error("Schedule with id {} not found", sid);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteSchedule(Long sid) {
        if (scheduleRepository.existsById(sid)) {
            scheduleRepository.deleteById(sid);
            return true;
        } else {
            log.error("Schedule with id {} not found", sid);
        }
        return false;
    }
}