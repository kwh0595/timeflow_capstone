package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    // 로그인된 사용자가 시행자로 되어 있는 일정 조회
    @Query("SELECT s FROM ScheduleEntity s JOIN s.assignees a WHERE a.userId = :userId")
    List<ScheduleEntity> findAllByAssigneeUserId(@Param("userId") Long userId);


}
