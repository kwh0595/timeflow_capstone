package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/scheduler")
    public String getSchedulerPage() {
        return "scheduler"; // templates 디렉토리의 scheduler.html 파일을 반환
    }

    @PostMapping("/schedule/personal")
    public ResponseEntity<ScheduleDTO> createPersonalSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpSession session) {
        ScheduleEntity savedSchedule = scheduleService.createPersonalSchedule(scheduleDTO, session);
        ScheduleDTO responseDTO = new ScheduleDTO(savedSchedule.getSid(), savedSchedule.getSname(), savedSchedule.getScontents(), savedSchedule.getStartDate(), savedSchedule.getEndDate(), savedSchedule.getSprocess(), savedSchedule.getScolor());
        return ResponseEntity.ok(responseDTO);
    }



    @PostMapping("/schedule/team/{teamId}")
    public String createTeamSchedule(@ModelAttribute ScheduleDTO scheduleDTO, @PathVariable Long teamId, HttpSession session) {
        scheduleService.createTeamSchedule(scheduleDTO, teamId, session);
        return "redirect:/scheduler";
    }

    @PutMapping("/api/schedules/{sid}")
    public ResponseEntity<String> updateSchedule(
            @PathVariable("sid") Long sid,
            @RequestBody ScheduleDTO scheduleDTO,
            HttpSession session) {
        boolean success = scheduleService.updateSchedule(sid, scheduleDTO, session);
        if (success) {
            return ResponseEntity.ok("Schedule updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating schedule");
        }
    }


    @DeleteMapping("/api/schedules/{sid}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("sid") Long sid, HttpSession session) {
        boolean success = scheduleService.deleteSchedule(sid, session);
        if (success) {
            return ResponseEntity.ok("Schedule deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting schedule");
        }
    }

    @PostMapping("/api/schedules")
    public ResponseEntity<String> addApiSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpSession session) {
        // ScheduleDTO를 ScheduleEntity로 변환하고 저장하는 서비스 호출
        ScheduleEntity schedule = scheduleService.createPersonalSchedule(scheduleDTO, session);
        // 저장이 성공적으로 이루어졌는지 확인
        if (schedule != null) {
            return ResponseEntity.ok("Schedule added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding schedule");
        }
    }
    @GetMapping("/api/schedules/user")
    public ResponseEntity<List<ScheduleDTO>> getUserSchedules(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<ScheduleEntity> schedules = scheduleService.getUserSchedules(userId);
        List<ScheduleDTO> scheduleDTOs = schedules.stream()
                .map(schedule -> new ScheduleDTO(
                        schedule.getSid(),
                        schedule.getSname(),
                        schedule.getScontents(),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        schedule.getSprocess(),
                        schedule.getScolor()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(scheduleDTOs);
    }
}

