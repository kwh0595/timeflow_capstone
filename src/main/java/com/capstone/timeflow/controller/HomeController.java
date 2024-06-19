package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.CustomUser;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public String timeflowPage(){
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }
    @GetMapping("/user/mypage")
    public String mypage() {
        return "mypage";
    }
    @GetMapping("/user/mypage/newpassword")
    public String newPasswordPage(){
        return "newpassword";
    }
    @PostMapping("/api/logout")
    public ResponseEntity<Map<String, Boolean>> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true); // 성공 응답
        return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK 응답
    }
}