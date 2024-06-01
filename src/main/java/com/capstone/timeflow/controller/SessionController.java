package com.capstone.timeflow.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/setSessionUsername")
    public String setSessionUsername(HttpSession session, @RequestParam String userName) {
        session.setAttribute("userName", userName);
        return "Session username set to " + userName;
    }

    @GetMapping("/getSessionUsername")
    public Map<String, String> getSessionUsername(HttpSession session) {
        System.out.println("getSessionController가 실행되었습니다.");
        String username = (String) session.getAttribute("userName");
        Map<String, String> response = new HashMap<>();
        if (username == null) {
            response.put("userName", "No username set in session");
        } else {
            response.put("userName", username);
        }
        return response;
    }
}

