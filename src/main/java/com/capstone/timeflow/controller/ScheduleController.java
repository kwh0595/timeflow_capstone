package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.repository.UserRepository;
import com.capstone.timeflow.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//
@Controller
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
// (/schedules)는 스케쥴 페이지를 반환하는 역활
    //이 경로는 페잊만 보여줌
    @GetMapping("/scheduler")
    public String getSchedulerPage() {
        return "scheduler"; // templates 디렉토리의 scheduler.html 파일을 반환
    }

    @PostMapping("/schedule")
    public String createSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {
        scheduleService.createSchedule(scheduleDTO);
        return "redirect:/scheduler";
    }


    // (/api/schedules)는 새로운 스케줄을 추가하는 APi  엔드포인트
    //이 경로는 데이터 삽입 수정 삭제를 담당함.

    //데이터 업데이트 부분 sid를 기준으로 데이터를 수정함
    @PutMapping("/api/schedules/{sid}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long sid, @RequestParam String sprocess) {
        boolean success = scheduleService.updateSchedule(sid, sprocess);
        if (success) {
            return ResponseEntity.ok("Schedule updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating schedule");
        }
    }

    //데이터 삭제 부분. sid를 기준으로 데이터 삭제함
    @DeleteMapping("/api/schedules/{sid}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long sid) {

        boolean success = scheduleService.deleteSchedule(sid);
        if (success) {
            return ResponseEntity.ok("Schedule deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting schedule");
        }
    }

    @PostMapping("/api/schedules")
    public ResponseEntity<String> addApiSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        // ScheduleDTO를 ScheduleEntity로 변환하고 저장하는 서비스 호출
        ScheduleEntity schedule = scheduleService.createSchedule(scheduleDTO);
        // 저장이 성공적으로 이루어졌는지 확인
        if (schedule != null) {
            return ResponseEntity.ok("Schedule added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding schedule");
        }
    }
}

