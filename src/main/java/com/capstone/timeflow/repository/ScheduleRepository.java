package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByRegistrarUserId(Long userId);
}
