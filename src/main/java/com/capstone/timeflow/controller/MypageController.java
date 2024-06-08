package com.capstone.timeflow.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
public class MypageController {

    @GetMapping("/api/user")
    public Map<String, String> getSession(HttpSession session ) {
        // 세션에서 사용자 정보 가져오기
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String userMail = (String) session.getAttribute("userMail");

        // 반환할 응답 객체 생성
        Map<String, String> response = new HashMap<>();

        // 사용자 ID, 이름, 메일을 검사하여 응답에 추가
        if (userId == null) {
            response.put("error", "No userId set in session");
        } else {
            // 사용자 ID는 String 타입으로 변환하여 추가
            response.put("userId", String.valueOf(userId));
            // 사용자 이름과 메일 추가
            response.put("userName", userName != null ? userName : "No userName set in session");
            response.put("userMail", userMail != null ? userMail : "No userMail set in session");
        }

        // 디버그 목적으로 콘솔에 로그 출력
        System.out.println("UserID: " + userId + ", UserName: " + userName + ", UserMail: " + userMail);

        return response;
    }

}