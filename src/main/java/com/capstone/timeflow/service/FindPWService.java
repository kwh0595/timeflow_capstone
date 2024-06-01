package com.capstone.timeflow.service;

import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FindPWService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomPasswordService randomPasswordService;

    @Autowired
    private SendingMailService sendingMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetPasswordAndSendEmail(String userMail) {
        UserEntity user = userRepository.findByUserMail(userMail).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String randomPassword = randomPasswordService.generateRandomPassword();
        user.setUserPassword(passwordEncoder.encode(randomPassword));
        System.out.println(randomPassword);
        userRepository.save(user);
        sendingMailService.sendEmail(userMail, "Your new password", "Here is your new password: " + randomPassword);
    }
}
