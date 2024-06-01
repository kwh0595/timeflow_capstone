package com.capstone.timeflow.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomPasswordService {
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_CHAR = "!@#&()–[{}]:;',?/*";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_CHAR;
    private static final int PASSWORD_LENGTH = 10; // 원하는 길이로 조정

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW_BASE.length());
            char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }
}
