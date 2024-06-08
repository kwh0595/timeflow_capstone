package com.capstone.timeflow.repository;

import com.capstone.timeflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserNameAndUserBirth(String userName, LocalDate userBirth);
    Optional<UserEntity> findByUserMail(String userMail);
    Optional<UserEntity> findByUserName(String userName);
}