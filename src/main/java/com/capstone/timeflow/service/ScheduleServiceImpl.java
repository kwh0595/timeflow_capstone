package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.ScheduleRepository;
import com.capstone.timeflow.repository.TeamRepository;
import com.capstone.timeflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // 개인 일정 등록
    @Override
    public ScheduleEntity createPersonalSchedule(ScheduleDTO scheduleDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setSname(scheduleDTO.getSname());
        schedule.setScontents(scheduleDTO.getScontents());
        schedule.setStartDate(scheduleDTO.getStartdate());
        schedule.setEndDate(scheduleDTO.getEnddate());
        schedule.setSprocess(scheduleDTO.getSprocess());
        schedule.setScolor(scheduleDTO.getScolor());
        schedule.setRegistrar(user);
        schedule.getAssignees().add(user);
        schedule.setTeam(null); // 개인 일정은 팀 아이디가 null

        return scheduleRepository.save(schedule);
    }

    // 팀 일정 등록
    @Override
    public ScheduleEntity createTeamSchedule(ScheduleDTO scheduleDTO, Long teamId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TeamEntity team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setSname(scheduleDTO.getSname());
        schedule.setScontents(scheduleDTO.getScontents());
        schedule.setStartDate(scheduleDTO.getStartdate());
        schedule.setEndDate(scheduleDTO.getEnddate());
        schedule.setSprocess(scheduleDTO.getSprocess());
        schedule.setScolor(scheduleDTO.getScolor());
        schedule.setRegistrar(user);
        //schedule.getAssignees().add(user); // 기본적으로 등록자를 시행자로 설정
        schedule.setTeam(team); // 팀 일정의 경우 팀 아이디 설정
        if (scheduleDTO.getAssigneeUsernames() != null) {
            for (String userName : scheduleDTO.getAssigneeUsernames()) {
                UserEntity assignee = userRepository.findByUserName(userName)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userName));
                schedule.getAssignees().add(assignee);
            }
        }

        return scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleEntity createTeamSchedule(ScheduleDTO scheduleDTO, Long teamId, Long userId) {
        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TeamEntity team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setSname(scheduleDTO.getSname());
        schedule.setScontents(scheduleDTO.getScontents());
        schedule.setStartDate(scheduleDTO.getStartdate());
        schedule.setEndDate(scheduleDTO.getEnddate());
        schedule.setSprocess(scheduleDTO.getSprocess());
        schedule.setScolor(scheduleDTO.getScolor());
        schedule.setRegistrar(user);
        //schedule.getAssignees().add(user); // 기본적으로 등록자를 시행자로 설정
        schedule.setTeam(team); // 팀 일정의 경우 팀 아이디 설정
        if (scheduleDTO.getAssigneeUsernames() != null) {
            for (String userName : scheduleDTO.getAssigneeUsernames()) {
                UserEntity assignee = userRepository.findByUserName(userName)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userName));
                schedule.getAssignees().add(assignee);
            }
        }

        return scheduleRepository.save(schedule);
    }

    // 일정 수정
    @Override
    public boolean updateSchedule(Long sid, ScheduleDTO scheduleDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }

        ScheduleEntity schedule = scheduleRepository.findById(sid).orElse(null);
        if (schedule != null && schedule.getRegistrar().getUserId().equals(userId)) {
            schedule.setSname(scheduleDTO.getSname());
            schedule.setScontents(scheduleDTO.getScontents());
            schedule.setStartDate(scheduleDTO.getStartdate());
            schedule.setEndDate(scheduleDTO.getEnddate());
            schedule.setSprocess(scheduleDTO.getSprocess());
            schedule.setScolor(scheduleDTO.getScolor());
            scheduleRepository.save(schedule);
            return true;
        } else {
            log.error("Schedule with id {} not found or user {} is not the registrar", sid, userId);
            return false;
        }
    }

    // 일정 삭제
    @Override
    @Transactional
    public boolean deleteSchedule(Long sid, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }

        ScheduleEntity schedule = scheduleRepository.findById(sid).orElse(null);
        if (schedule != null && schedule.getRegistrar().getUserId().equals(userId)) {
            scheduleRepository.deleteById(sid);
            return true;
        } else {
            log.error("Schedule with id {} not found or user {} is not the registrar", sid, userId);
        }
        return false;
    }

    @Override
    public List<ScheduleEntity> getSchedulesByAssignee(Long userId) {
        return scheduleRepository.findAllByAssigneeUserId(userId);
    }

    @Override
    public List<ScheduleEntity> getUserSchedules(Long userId){
        return getSchedulesByAssignee(userId);
    }

}