package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
