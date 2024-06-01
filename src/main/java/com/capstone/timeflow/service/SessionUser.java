package com.capstone.timeflow.service;

import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionUser {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
