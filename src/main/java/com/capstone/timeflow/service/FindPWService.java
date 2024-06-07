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
        sendingMailService.sendEmail(userMail, "TimeFlow 비밀번호 변경 안내",
                "귀하의 요청에 따라 기존의 비밀번호가 변경되었음을 고지드립니다. \n\n" +
                        "현 시각 이후에는 아래 안내된 변경된 비밀번호를 통해 로그인 가능하며,\n" +
                        "보안을 위해 빠른 시일 내에 마이페이지를 통해 새로운 비밀번호로 변경하시는 것을 권장드립니다. \n\n" +
                        "비밀번호 변경 방법은 마이페이지 > 비밀번호 변경 을 통해 가능합니다.\n\n"
                        + "변경된 비밀번호 : " + randomPassword + "From TimeFLow 팀");
    }
}
