package com.capstone.timeflow.service;

import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FindEmailService {

    @Autowired
    private UserRepository userRepository;

    public String findEmailByNameAndBirthDate(String userName, LocalDate userBirth) {
        Optional<UserEntity> user = userRepository.findByUserNameAndUserBirth(userName, userBirth);
        if (user.isPresent()) {
            return user.get().getUserMail();
        } else {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
    }
}
