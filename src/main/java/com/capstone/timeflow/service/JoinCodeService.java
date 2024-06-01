package com.capstone.timeflow.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class JoinCodeService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String joinCodeGenerate(int length) {
        if (length < 5 || length > 10) {
            throw new IllegalArgumentException("Length must be between 5 and 10.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}