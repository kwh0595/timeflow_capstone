package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;

public interface UserService {
    void save(UserDTO userDTO);
    UserEntity updateUser(String name, UserDTO userDTO);
    boolean isPasswordCorrect(String username, String password);
    boolean updatePassword(String username, String newPassword);
}

